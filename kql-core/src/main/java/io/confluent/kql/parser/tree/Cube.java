/**
 * Copyright 2017 Confluent Inc.
 *
 **/
package io.confluent.kql.parser.tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public class Cube
    extends GroupingElement {

  private final List<QualifiedName> columns;

  public Cube(List<QualifiedName> columns) {
    this(Optional.empty(), columns);
  }

  public Cube(NodeLocation location, List<QualifiedName> columns) {
    this(Optional.of(location), columns);
  }

  private Cube(Optional<NodeLocation> location, List<QualifiedName> columns) {
    super(location);
    requireNonNull(columns, "columns is null");
    this.columns = columns;
  }

  public List<QualifiedName> getColumns() {
    return columns;
  }

  @Override
  public List<Set<Expression>> enumerateGroupingSets() {
    return ImmutableList.copyOf(Sets.powerSet(columns.stream()
                                                  .map(QualifiedNameReference::new)
                                                  .collect(toSet())));
  }

  @Override
  protected <R, C> R accept(AstVisitor<R, C> visitor, C context) {
    return visitor.visitCube(this, context);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cube cube = (Cube) o;
    return Objects.equals(columns, cube.columns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(columns);
  }

  @Override
  public String toString() {
    return toStringHelper(this)
        .add("columns", columns)
        .toString();
  }
}

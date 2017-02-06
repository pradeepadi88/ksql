/**
 * Copyright 2017 Confluent Inc.
 *
 **/
package io.confluent.kql.parser.tree;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class CurrentTime
    extends Expression {

  private final Type type;
  private final Integer precision;

  public enum Type {
    TIME("current_time"),
    DATE("current_date"),
    TIMESTAMP("current_timestamp"),
    LOCALTIME("localtime"),
    LOCALTIMESTAMP("localtimestamp");

    private final String name;

    Type(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  public CurrentTime(Type type) {
    this(Optional.empty(), type, null);
  }

  public CurrentTime(NodeLocation location, Type type) {
    this(Optional.of(location), type, null);
  }

  public CurrentTime(Type type, Integer precision) {
    this(Optional.empty(), type, precision);
  }

  public CurrentTime(NodeLocation location, Type type, Integer precision) {
    this(Optional.of(location), type, precision);
  }

  private CurrentTime(Optional<NodeLocation> location, Type type, Integer precision) {
    super(location);
    requireNonNull(type, "type is null");
    this.type = type;
    this.precision = precision;
  }

  public Type getType() {
    return type;
  }

  public Integer getPrecision() {
    return precision;
  }

  @Override
  public <R, C> R accept(AstVisitor<R, C> visitor, C context) {
    return visitor.visitCurrentTime(this, context);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    CurrentTime that = (CurrentTime) o;
    return (type == that.type) &&
           Objects.equals(precision, that.precision);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, precision);
  }
}

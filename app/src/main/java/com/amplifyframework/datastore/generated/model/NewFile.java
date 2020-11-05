package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the NewFile type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "NewFiles")
public final class NewFile implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField TITLE = field("title");
  public static final QueryField BELONGS_TO = field("newFileBelongsToId");


  public final @ModelField(targetType="ID", isRequired = true) String id;
  public final @ModelField(targetType="String") String title;
  public final @ModelField(targetType="Task", isRequired = true) @BelongsTo(targetName = "newFileBelongsToId", type = Task.class) Task belongsTo;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public Task getBelongsTo() {
      return belongsTo;
  }
  
  public NewFile(String id, String title, Task belongsTo) {
    this.id = id;
    this.title = title;
    this.belongsTo = belongsTo;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      NewFile newFile = (NewFile) obj;
      return ObjectsCompat.equals(getId(), newFile.getId()) &&
              ObjectsCompat.equals(getTitle(), newFile.getTitle()) &&
              ObjectsCompat.equals(getBelongsTo(), newFile.getBelongsTo());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBelongsTo())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("NewFile {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("belongsTo=" + String.valueOf(getBelongsTo()))
      .append("}")
      .toString();
  }
  
  public static BelongsToStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static NewFile justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new NewFile(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      belongsTo);
  }
  public interface BelongsToStep {
    BuildStep belongsTo(Task belongsTo);
  }
  

  public interface BuildStep {
    NewFile build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep title(String title);
  }
  

  public static class Builder implements BelongsToStep, BuildStep {
    private String id;
    private Task belongsTo;
    private String title;
    @Override
     public NewFile build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new NewFile(
          id,
          title,
          belongsTo);
    }
    
    @Override
     public BuildStep belongsTo(Task belongsTo) {
        Objects.requireNonNull(belongsTo);
        this.belongsTo = belongsTo;
        return this;
    }
    
    @Override
     public BuildStep title(String title) {
        this.title = title;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, Task belongsTo) {
      super.id(id);
      super.belongsTo(belongsTo)
        .title(title);
    }
    
    @Override
     public CopyOfBuilder belongsTo(Task belongsTo) {
      return (CopyOfBuilder) super.belongsTo(belongsTo);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
  }
  
}

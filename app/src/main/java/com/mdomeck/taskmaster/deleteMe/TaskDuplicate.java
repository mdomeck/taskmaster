package com.mdomeck.taskmaster.deleteMe;

import androidx.annotation.NonNull;
import androidx.core.util.ObjectsCompat;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.UUID;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

public class TaskDuplicate {

//    /** This is an auto generated class representing the Task type in your schema. */
//    @SuppressWarnings("all")
//
//    @Entity
//    @ModelConfig(pluralName = "Tasks")
//    public final class Task implements Model {
//        public static final QueryField ID = field("id");
//        public static final QueryField TITLE = field("title");
//        public static final QueryField BODY = field("body");
//        public static final QueryField STATE = field("state");
//
//        @NonNull
//        @PrimaryKey
//        public final @ModelField(targetType="ID", isRequired = true) String id;
//        public final @ModelField(targetType="String") String title;
//        public final @ModelField(targetType="String") String body;
//        public final @ModelField(targetType="String") String state;
//        public String getId() {
//            return id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public String getBody() {
//            return body;
//        }
//
//        public String getState() {
//            return state;
//        }
//
//        public Task(String id, String title, String body, String state) {
//            this.id = id;
//            this.title = title;
//            this.body = body;
//            this.state = state;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) {
//                return true;
//            } else if(obj == null || getClass() != obj.getClass()) {
//                return false;
//            } else {
//                com.amplifyframework.datastore.generated.model.Task task = (com.amplifyframework.datastore.generated.model.Task) obj;
//                return ObjectsCompat.equals(getId(), task.getId()) &&
//                        ObjectsCompat.equals(getTitle(), task.getTitle()) &&
//                        ObjectsCompat.equals(getBody(), task.getBody()) &&
//                        ObjectsCompat.equals(getState(), task.getState());
//            }
//        }
//
//        @Override
//        public int hashCode() {
//            return new StringBuilder()
//                    .append(getId())
//                    .append(getTitle())
//                    .append(getBody())
//                    .append(getState())
//                    .toString()
//                    .hashCode();
//        }
//
//        @Override
//        public String toString() {
//            return new StringBuilder()
//                    .append("Task {")
//                    .append("id=" + String.valueOf(getId()) + ", ")
//                    .append("title=" + String.valueOf(getTitle()) + ", ")
//                    .append("body=" + String.valueOf(getBody()) + ", ")
//                    .append("state=" + String.valueOf(getState()))
//                    .append("}")
//                    .toString();
//        }
//
//        public static com.amplifyframework.datastore.generated.model.Task.BuildStep builder() {
//            return new com.amplifyframework.datastore.generated.model.Task.Builder();
//        }
//
//        /**
//         * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
//         * This is a convenience method to return an instance of the object with only its ID populated
//         * to be used in the context of a parameter in a delete mutation or referencing a foreign key
//         * in a relationship.
//         * @param id the id of the existing item this instance will represent
//         * @return an instance of this model with only ID populated
//         * @throws IllegalArgumentException Checks that ID is in the proper format
//         */
//        public static com.amplifyframework.datastore.generated.model.Task justId(String id) {
//            try {
//                UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
//            } catch (Exception exception) {
//                throw new IllegalArgumentException(
//                        "Model IDs must be unique in the format of UUID. This method is for creating instances " +
//                                "of an existing object with only its ID field for sending as a mutation parameter. When " +
//                                "creating a new object, use the standard builder method and leave the ID field blank."
//                );
//            }
//            return new com.amplifyframework.datastore.generated.model.Task(
//                    id,
//                    null,
//                    null,
//                    null
//            );
//        }
//
//        public com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder copyOfBuilder() {
//            return new com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder(id,
//                    title,
//                    body,
//                    state);
//        }
//        public interface BuildStep {
//            com.amplifyframework.datastore.generated.model.Task build();
//            com.amplifyframework.datastore.generated.model.Task.BuildStep id(String id) throws IllegalArgumentException;
//            com.amplifyframework.datastore.generated.model.Task.BuildStep title(String title);
//            com.amplifyframework.datastore.generated.model.Task.BuildStep body(String body);
//            com.amplifyframework.datastore.generated.model.Task.BuildStep state(String state);
//        }
//
//
//        public static class Builder implements com.amplifyframework.datastore.generated.model.Task.BuildStep {
//            private String id;
//            private String title;
//            private String body;
//            private String state;
//            @Override
//            public com.amplifyframework.datastore.generated.model.Task build() {
//                String id = this.id != null ? this.id : UUID.randomUUID().toString();
//
//                return new com.amplifyframework.datastore.generated.model.Task(
//                        id,
//                        title,
//                        body,
//                        state);
//            }
//
//            @Override
//            public com.amplifyframework.datastore.generated.model.Task.BuildStep title(String title) {
//                this.title = title;
//                return this;
//            }
//
//            @Override
//            public com.amplifyframework.datastore.generated.model.Task.BuildStep body(String body) {
//                this.body = body;
//                return this;
//            }
//
//            @Override
//            public com.amplifyframework.datastore.generated.model.Task.BuildStep state(String state) {
//                this.state = state;
//                return this;
//            }
//
//            /**
//             * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
//             * This should only be set when referring to an already existing object.
//             * @param id id
//             * @return Current Builder instance, for fluent method chaining
//             * @throws IllegalArgumentException Checks that ID is in the proper format
//             */
//            public com.amplifyframework.datastore.generated.model.Task.BuildStep id(String id) throws IllegalArgumentException {
//                this.id = id;
//
//                try {
//                    UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
//                } catch (Exception exception) {
//                    throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
//                            exception);
//                }
//
//                return this;
//            }
//        }
//
//
//        public final class CopyOfBuilder extends com.amplifyframework.datastore.generated.model.Task.Builder {
//            private CopyOfBuilder(String id, String title, String body, String state) {
//                super.id(id);
//                super.title(title)
//                        .body(body)
//                        .state(state);
//            }
//
//            @Override
//            public com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder title(String title) {
//                return (com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder) super.title(title);
//            }
//
//            @Override
//            public com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder body(String body) {
//                return (com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder) super.body(body);
//            }
//
//            @Override
//            public com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder state(String state) {
//                return (com.amplifyframework.datastore.generated.model.Task.CopyOfBuilder) super.state(state);
//            }
//        }
//
//    }

}

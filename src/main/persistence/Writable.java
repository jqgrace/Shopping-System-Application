package persistence;

import org.json.JSONObject;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java.
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/Category.javapackage persistence;

public interface Writable {
    // EFECTS: return a JSON object
    JSONObject toJson();
}

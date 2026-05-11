package org.datasource.mongodb.views.departamentscities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@JsonIgnoreProperties({"_id"})
@Data @AllArgsConstructor @NoArgsConstructor(force = true)
public class DepartamentsListView {
    private List<DepartamentView> departaments;
}
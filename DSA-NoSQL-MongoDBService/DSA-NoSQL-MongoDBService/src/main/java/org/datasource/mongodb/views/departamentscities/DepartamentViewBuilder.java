package org.datasource.mongodb.views.departamentscities;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.datasource.mongodb.MongoDataSourceConnector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartamentViewBuilder {

    private DepartamentsListView departamentsListView;
    private List<DepartamentView> departamentsViewList;
    private List<CityView> citiesViewList;

    public DepartamentsListView getDepartamentsListView() { return departamentsListView; }
    public List<DepartamentView> getDepartamentsViewList() { return departamentsViewList; }
    public List<CityView> getCitiesViewList() { return citiesViewList; }

    private MongoDataSourceConnector dataSourceConnector;

    public DepartamentViewBuilder(MongoDataSourceConnector dataSourceConnector) {
        this.dataSourceConnector = dataSourceConnector;
    }

    public DepartamentViewBuilder build() throws Exception {
        return this.select().map();
    }

    private DepartamentViewBuilder map() {
        this.departamentsViewList = this.departamentsListView.getDepartaments();
        this.citiesViewList = new ArrayList<>();
        for (DepartamentView d : departamentsViewList)
            this.citiesViewList.addAll(d.getCities());
        return this;
    }

    public DepartamentViewBuilder select() throws Exception {
        MongoDatabase db = dataSourceConnector.getMongoDatabase();
        MongoCollection<DepartamentsListView> col =
                db.getCollection("DepartamentsCities", DepartamentsListView.class);
        this.departamentsListView = col.find().first();
        if (departamentsListView != null)
            departamentsListView.getDepartaments().forEach(System.out::println);
        return this;
    }
}

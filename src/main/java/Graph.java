import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Graph {
    private int vertices;
    private List<Edge> edges;

    public Graph(int vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public int getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public static List<Graph> loadFromJSON(String fileName) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        JSONObject obj = new JSONObject(content);
        JSONArray graphs = obj.getJSONArray("graphs");

        List<Graph> graphList = new ArrayList<>();
        for (int i = 0; i < graphs.length(); i++) {
            JSONObject g = graphs.getJSONObject(i);
            int vertices = g.getInt("vertices");
            JSONArray edgesArray = g.getJSONArray("edges");

            List<Edge> edges = new ArrayList<>();
            for (int j = 0; j < edgesArray.length(); j++) {
                JSONObject e = edgesArray.getJSONObject(j);
                edges.add(new Edge(e.getInt("u"), e.getInt("v"), e.getInt("weight")));
            }
            graphList.add(new Graph(vertices, edges));
        }
        return graphList;
    }
}

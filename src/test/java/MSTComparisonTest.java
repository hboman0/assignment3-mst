import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MSTComparisonTest {

    static JSONObject inputData;

    @BeforeAll
    static void loadData() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("assign_3_input.json")));
        inputData = new JSONObject(content);
        assertNotNull(inputData, "Input JSON should not be null");
    }

    @Test
    void testMSTCorrectnessAndPerformance() {
        JSONArray graphs = inputData.getJSONArray("graphs");

        for (int i = 0; i < graphs.length(); i++) {
            JSONObject g = graphs.getJSONObject(i);
            String name = g.getString("name");
            int vertices = g.getInt("vertices");
            JSONArray edgesArray = g.getJSONArray("edges");

            List<MainMSTComparison.Edge> edges = new ArrayList<>();
            for (int j = 0; j < edgesArray.length(); j++) {
                JSONObject e = edgesArray.getJSONObject(j);
                edges.add(new MainMSTComparison.Edge(e.getInt("u"), e.getInt("v"), e.getInt("weight")));
            }

            JSONObject kruskalResult = MainMSTComparison.runKruskal(vertices, edges);
            JSONObject primResult = MainMSTComparison.runPrim(vertices, edges);

            int costK = kruskalResult.getInt("totalWeight");
            int costP = primResult.getInt("totalWeight");
            double timeK = kruskalResult.getDouble("executionTimeMs");
            double timeP = primResult.getDouble("executionTimeMs");

            System.out.println("\nGraph: " + name);
            System.out.println("Kruskal MST cost: " + costK + " | Prim MST cost: " + costP);
            System.out.println("Execution Times (ms): Kruskal=" + timeK + ", Prim=" + timeP);

            assertEquals(costK, costP, "MST total costs should be identical for Prim and Kruskal");
            assertTrue(timeK >= 0 && timeP >= 0, "Execution times must be non-negative");

            JSONArray mstK = kruskalResult.getJSONArray("mstEdges");
            JSONArray mstP = primResult.getJSONArray("mstEdges");

            assertEquals(vertices - 1, mstK.length(), "Kruskal MST should have V - 1 edges");
            assertEquals(vertices - 1, mstP.length(), "Prim MST should have V - 1 edges");

            assertTrue(isConnected(vertices, mstK), "Kruskal MST must connect all vertices");
            assertTrue(isConnected(vertices, mstP), "Prim MST must connect all vertices");
        }
    }

    private boolean isConnected(int vertices, JSONArray mstEdges) {
        if (mstEdges.length() < vertices - 1) return false;

        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < mstEdges.length(); i++) {
            JSONObject e = mstEdges.getJSONObject(i);
            int u = e.getInt("u");
            int v = e.getInt("v");
            adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);
                for (int n : adj.getOrDefault(node, new ArrayList<>())) stack.push(n);
            }
        }
        return visited.size() == vertices;
    }
}

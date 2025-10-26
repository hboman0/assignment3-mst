import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.FileWriter;

public class MainMSTComparison {

    static class DSU {
        int[] parent, rank;
        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        void union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return;
            if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else { parent[rootY] = rootX; rank[rootX]++; }
        }
    }

    static class Edge implements Comparable<Edge> {
        int src, dest, weight;
        Edge(int s, int d, int w) { src = s; dest = d; weight = w; }
        public int compareTo(Edge o) { return this.weight - o.weight; }
    }

    public static JSONObject runKruskal(int vertices, List<Edge> edges) {
        long comparisons = 0, findOps = 0, unionOps = 0;
        long start = System.nanoTime();

        Collections.sort(edges);
        DSU dsu = new DSU(vertices);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            int rootU = dsu.find(e.src);
            int rootV = dsu.find(e.dest);
            findOps += 2;
            comparisons++;
            if (rootU != rootV) {
                mst.add(e);
                dsu.union(rootU, rootV);
                unionOps++;
            }
            if (mst.size() == vertices - 1) break;
        }

        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        int totalWeight = 0;
        JSONArray mstEdges = new JSONArray();
        for (Edge e : mst) {
            totalWeight += e.weight;
            mstEdges.put(new JSONObject()
                    .put("u", e.src)
                    .put("v", e.dest)
                    .put("weight", e.weight));
        }

        JSONObject result = new JSONObject();
        result.put("algorithm", "Kruskal");
        result.put("totalWeight", totalWeight);
        result.put("comparisons", comparisons);
        result.put("findOps", findOps);
        result.put("unionOps", unionOps);
        result.put("executionTimeMs", durationMs);
        result.put("mstEdges", mstEdges);
        return result;
    }

    public static JSONObject runPrim(int vertices, List<Edge> edges) {
        long comparisons = 0, extractMinOps = 0, updateKeyOps = 0;
        long start = System.nanoTime();

        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) adj.add(new ArrayList<Edge>());
        for (Edge e : edges) {
            adj.get(e.src).add(new Edge(e.src, e.dest, e.weight));
            adj.get(e.dest).add(new Edge(e.dest, e.src, e.weight));
        }

        boolean[] inMST = new boolean[vertices];
        int[] key = new int[vertices];
        int[] parent = new int[vertices];
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        key[0] = 0;
        pq.add(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int[] node = pq.poll();
            int u = node[0];
            extractMinOps++;
            inMST[u] = true;

            for (Edge e : adj.get(u)) {
                int v = e.dest;
                comparisons++;
                if (!inMST[v] && e.weight < key[v]) {
                    key[v] = e.weight;
                    parent[v] = u;
                    pq.add(new int[]{v, key[v]});
                    updateKeyOps++;
                }
            }
        }

        long end = System.nanoTime();
        double durationMs = (end - start) / 1_000_000.0;

        List<Edge> mst = new ArrayList<>();
        int totalWeight = 0;
        JSONArray mstEdges = new JSONArray();
        for (int i = 1; i < vertices; i++) {
            mst.add(new Edge(parent[i], i, key[i]));
            totalWeight += key[i];
            mstEdges.put(new JSONObject()
                    .put("u", parent[i])
                    .put("v", i)
                    .put("weight", key[i]));
        }

        JSONObject result = new JSONObject();
        result.put("algorithm", "Prim");
        result.put("totalWeight", totalWeight);
        result.put("comparisons", comparisons);
        result.put("extractMinOps", extractMinOps);
        result.put("updateKeyOps", updateKeyOps);
        result.put("executionTimeMs", durationMs);
        result.put("mstEdges", mstEdges);
        return result;
    }

    public static void main(String[] args) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("assign_3_input.json")));
            JSONObject obj = new JSONObject(content);
            JSONArray graphs = obj.getJSONArray("graphs");

            JSONArray allResults = new JSONArray();

            for (int i = 0; i < graphs.length(); i++) {
                JSONObject g = graphs.getJSONObject(i);
                String name = g.getString("name");
                int vertices = g.getInt("vertices");
                JSONArray edgesArray = g.getJSONArray("edges");

                List<Edge> edges = new ArrayList<>();
                for (int j = 0; j < edgesArray.length(); j++) {
                    JSONObject e = edgesArray.getJSONObject(j);
                    edges.add(new Edge(e.getInt("u"), e.getInt("v"), e.getInt("weight")));
                }

                System.out.println("\n===== " + name + " =====");
                JSONObject kruskalResult = runKruskal(vertices, edges);
                JSONObject primResult = runPrim(vertices, edges);

                System.out.println("Kruskal MST weight: " + kruskalResult.getInt("totalWeight"));
                System.out.println("Prim MST weight: " + primResult.getInt("totalWeight"));
                System.out.println("Execution Times (ms): Kruskal=" + kruskalResult.getDouble("executionTimeMs")
                        + ", Prim=" + primResult.getDouble("executionTimeMs"));

                JSONObject combined = new JSONObject();
                combined.put("graph", name);
                combined.put("vertices", vertices);
                combined.put("edges", edges.size());
                combined.put("kruskal", kruskalResult);
                combined.put("prim", primResult);

                allResults.put(combined);
            }

            JSONObject output = new JSONObject();
            output.put("MST_Comparison_Results", allResults);

            FileWriter file = new FileWriter("assign_3_output.json");
            file.write(output.toString(4));
            file.close();

            System.out.println("\n✅ Results saved to assign_3_output.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)

Student: Sovet Kazhmurat

Group: SE-2431

Language: Java (JSON input/output)

Algorithms Implemented: Prim’s and Kruskal’s

1. Summary of Input Data and Algorithm Results

The purpose of this project was to implement Prim’s and Kruskal’s algorithms to determine the Minimum Spanning Tree (MST) of a transportation network. Each city network was represented as an undirected weighted graph, where vertices correspond to city districts, edges correspond to possible roads, and weights represent road construction costs.

The input data was read from a JSON file (assign_3_input.json), and the results were saved in another JSON file (assign_3_output.json). Each dataset contained the number of vertices, edges, and edge weights. The output file included the MST edges, total cost, operation counts, and execution times for both algorithms.

The table below summarizes the results obtained for three datasets (small, medium, and large graphs):
<img width="1249" height="165" alt="Снимок экрана 2025-10-26 151053" src="https://github.com/user-attachments/assets/e846efcb-a7bc-472c-a200-6b4378a42287" />

2. Comparison Between Prim’s and Kruskal’s Algorithms

Theoretical comparison:
Kruskal’s algorithm follows an edge-based greedy approach, building the MST by repeatedly adding the smallest available edge that does not form a cycle. It requires sorting all edges and uses a Disjoint Set Union (DSU) structure for cycle detection. Complexity: O(E log E)

Prim’s algorithm, on the other hand, is vertex-based. It starts from one vertex and grows the MST by adding the smallest edge connecting a vertex in the MST to one outside of it. It typically uses a priority queue (min-heap) for edge selection. Complexity: O(E log V)

Operation counts:
<img width="745" height="161" alt="image" src="https://github.com/user-attachments/assets/fd64b494-753f-474f-9311-0d7366951940" />


Practical Comparison (from results):
In practical testing, Kruskal’s algorithm consistently achieved lower execution times than Prim’s across all tested graph sizes. Although both algorithms produced identical MST total costs, Kruskal required fewer or equal total operations in most cases. This difference arises because Kruskal sorts all edges once and efficiently merges components using the Disjoint Set Union (DSU) structure, while Prim repeatedly updates priority queues during edge relaxation.

In theory, Prim’s algorithm should outperform Kruskal’s on dense graphs because it doesn’t need to sort all edges. However, in my implementation, the graph is relatively small (30 vertices, ~400 edges). Java’s built-in sorting function is extremely optimized, and the DSU operations are nearly constant-time. Meanwhile, Prim’s use of a PriorityQueue adds object and heap overhead. So, for smaller or moderately dense graphs, Kruskal can still appear faster in practice.
<img width="422" height="90" alt="Снимок экрана 2025-10-27 015148" src="https://github.com/user-attachments/assets/22484a50-5696-438e-a992-aab965951e7e" />

If we tested much larger graphs (200v, 10,000+e)
<img width="424" height="90" alt="Снимок экрана 2025-10-27 015519" src="https://github.com/user-attachments/assets/d88e931a-bc5a-403a-a4ca-e838ecac5ef4" />

In all tested graph sizes and densities — from small (6 vertices) to large, dense networks (200 vertices, 10,000+ edges) — Kruskal’s algorithm consistently achieved lower execution times compared to Prim’s algorithm.
Although theoretical analysis suggests that Prim’s algorithm should be more efficient for dense graphs due to its vertex-based approach, practical implementation factors can alter this outcome.

In particular:

Kruskal’s single global edge sort and efficient Disjoint Set Union (DSU) operations are highly optimized and scale well.

Prim’s algorithm involves repeated priority queue updates, which introduce constant-time overhead in Java’s PriorityQueue implementation.

As a result, Kruskal performs faster even in dense graphs, while both algorithms still produce identical MST costs.

Both algorithms demonstrated excellent scalability, with execution times under 25 milliseconds even for large, dense graphs of 200 vertices. This confirms that both methods are suitable for transportation network optimization, but Kruskal’s algorithm offers better real-world efficiency in Java.



3. Conclusions and Recommendations

Both Prim’s and Kruskal’s algorithms were correctly implemented and verified using multiple datasets. The results confirmed that both produce the same MST total weight and perform efficiently for different graph sizes.

Despite theoretical expectations, experimental results show that Kruskal’s algorithm outperforms Prim’s even on dense graphs.
Therefore, for practical Java implementations involving transportation networks of varying density, Kruskal’s algorithm is recommended due to its consistent performance and lower execution time.
Both algorithms, however, produce identical MST total weights, confirming their correctness and reliability.

Summary of recommendations:

Use Kruskal’s algorithm for both sparse and dense graphs, as it demonstrated consistently faster execution and fewer total operations in practical tests.

Prim’s algorithm remains a valid alternative, especially when the graph is stored as an adjacency list or when incremental MST updates are required.

Both algorithms produced identical MST total costs, confirming the correctness and reliability of the implementations.

In real-world Java applications, Kruskal’s algorithm is generally more efficient and easier to implement when working with edge-based data such as transportation networks.

Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)

Student: Sovet Kazhmurat

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

Prim’s algorithm remains efficient and predictable, particularly in dense graphs, where adjacency lists and heaps optimize local edge selection. Kruskal, however, demonstrates stronger performance for sparse to moderately dense graphs, as seen in the tests. Both algorithms scale well — with execution times remaining under 2 milliseconds even for 20 vertices — confirming their suitability for city transportation network optimization.

But in theory, Prim’s algorithm should outperform Kruskal’s on dense graphs because it doesn’t need to sort all edges. However, in my implementation, the graph is relatively small (30 vertices, ~400 edges). Java’s built-in sorting function is extremely optimized, and the DSU operations are nearly constant-time. Meanwhile, Prim’s use of a PriorityQueue adds object and heap overhead. So, for smaller or moderately dense graphs, Kruskal can still appear faster in practice.
<img width="422" height="90" alt="Снимок экрана 2025-10-27 015148" src="https://github.com/user-attachments/assets/22484a50-5696-438e-a992-aab965951e7e" />

If we tested much larger graphs, Prim would begin to show its advantage:
<img width="424" height="90" alt="Снимок экрана 2025-10-27 015519" src="https://github.com/user-attachments/assets/d88e931a-bc5a-403a-a4ca-e838ecac5ef4" />



3. Conclusions and Recommendations

Both Prim’s and Kruskal’s algorithms were correctly implemented and verified using multiple datasets. The results confirmed that both produce the same MST total weight and perform efficiently for different graph sizes.

In terms of efficiency, Kruskal’s algorithm generally performed faster and required fewer comparisons and unions. It is therefore more suitable for sparse graphs or when the input is given as an edge list.
Prim’s algorithm, while slightly slower in these experiments, is often preferred for dense graphs or when an adjacency list is already available.

Summary of recommendations:

Use Kruskal’s algorithm for sparse graphs or edge-list representations.

Use Prim’s algorithm for dense graphs or adjacency-list-based graphs.

Both algorithms produce identical MST costs, ensuring correctness.

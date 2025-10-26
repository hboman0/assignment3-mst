Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)

Student: Sovet Kazhmurat

Language: Java (JSON input/output)

Algorithms Implemented: Prim’s and Kruskal’s

1. Summary of Input Data and Algorithm Results

The purpose of this project was to implement Prim’s and Kruskal’s algorithms to determine the Minimum Spanning Tree (MST) of a transportation network. Each city network was represented as an undirected weighted graph, where vertices correspond to city districts, edges correspond to possible roads, and weights represent road construction costs.

The input data was read from a JSON file (assign_3_input.json), and the results were saved in another JSON file (assign_3_output.json). Each dataset contained the number of vertices, edges, and edge weights. The output file included the MST edges, total cost, operation counts, and execution times for both algorithms.

The table below summarizes the results obtained for three datasets (small, medium, and large graphs):
<img width="1249" height="165" alt="Снимок экрана 2025-10-26 151053" src="https://github.com/user-attachments/assets/a62d2ec8-2463-467a-9782-0b2b6426b126" />

2. Comparison Between Prim’s and Kruskal’s Algorithms

Theoretical comparison:
Kruskal’s algorithm follows an edge-based greedy approach, building the MST by repeatedly adding the smallest available edge that does not form a cycle. It requires sorting all edges and uses a Disjoint Set Union (DSU) structure for cycle detection. Complexity: O(E log E)

Prim’s algorithm, on the other hand, is vertex-based. It starts from one vertex and grows the MST by adding the smallest edge connecting a vertex in the MST to one outside of it. It typically uses a priority queue (min-heap) for edge selection. Complexity: O(E log V)

Practical Comparison (from results):
In practical testing, Kruskal’s algorithm consistently achieved lower execution times than Prim’s across all tested graph sizes. Although both algorithms produced identical MST total costs, Kruskal required fewer or equal total operations in most cases. This difference arises because Kruskal sorts all edges once and efficiently merges components using the Disjoint Set Union (DSU) structure, while Prim repeatedly updates priority queues during edge relaxation.

Prim’s algorithm remains efficient and predictable, particularly in dense graphs, where adjacency lists and heaps optimize local edge selection. Kruskal, however, demonstrates stronger performance for sparse to moderately dense graphs, as seen in the tests. Both algorithms scale well — with execution times remaining under 2 milliseconds even for 20 vertices — confirming their suitability for city transportation network optimization.
3. Conclusions and Recommendations

Both Prim’s and Kruskal’s algorithms were correctly implemented and verified using multiple datasets. The results confirmed that both produce the same MST total weight and perform efficiently for different graph sizes.

In terms of efficiency, Kruskal’s algorithm generally performed faster and required fewer comparisons and unions. It is therefore more suitable for sparse graphs or when the input is given as an edge list.
Prim’s algorithm, while slightly slower in these experiments, is often preferred for dense graphs or when an adjacency list is already available.

Summary of recommendations:

Use Kruskal’s algorithm for sparse graphs or edge-list representations.

Use Prim’s algorithm for dense graphs or adjacency-list-based graphs.

Both algorithms produce identical MST costs, ensuring correctness.

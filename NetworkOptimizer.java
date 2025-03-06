import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Node {
    int id, x, y;
    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}

class Edge implements Comparable<Edge> {
    Node src, dest;
    int cost, bandwidth;
    public Edge(Node src, Node dest, int cost, int bandwidth) {
        this.src = src;
        this.dest = dest;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }
    public int compareTo(Edge other) {
        return this.cost - other.cost;
    }
}

public class NetworkOptimizer extends JFrame {
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private int nodeId = 0;
    
    public NetworkOptimizer() {
        setTitle("Network Optimizer");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Edge edge : edges) {
                    g.drawLine(edge.src.x, edge.src.y, edge.dest.x, edge.dest.y);
                    g.drawString("$" + edge.cost + " B:" + edge.bandwidth, (edge.src.x + edge.dest.x) / 2, (edge.src.y + edge.dest.y) / 2);
                }
                for (Node node : nodes) {
                    g.fillOval(node.x - 10, node.y - 10, 20, 20);
                    g.drawString("N" + node.id, node.x + 10, node.y);
                }
            }
        };
        panel.setBounds(0, 0, 600, 500);
        add(panel);
        
        JButton addNodeBtn = new JButton("Add Node");
        addNodeBtn.setBounds(10, 510, 100, 30);
        addNodeBtn.addActionListener(e -> {
            int x = new Random().nextInt(500);
            int y = new Random().nextInt(400);
            nodes.add(new Node(nodeId++, x, y));
            panel.repaint();
        });
        add(addNodeBtn);
        
        JButton addEdgeBtn = new JButton("Add Edge");
        addEdgeBtn.setBounds(120, 510, 100, 30);
        addEdgeBtn.addActionListener(e -> {
            if (nodes.size() < 2) return;
            Node src = nodes.get(new Random().nextInt(nodes.size()));
            Node dest = nodes.get(new Random().nextInt(nodes.size()));
            while (src == dest) dest = nodes.get(new Random().nextInt(nodes.size()));
            int cost = new Random().nextInt(50) + 1;
            int bandwidth = new Random().nextInt(100) + 1;
            edges.add(new Edge(src, dest, cost, bandwidth));
            panel.repaint();
        });
        add(addEdgeBtn);
        
        JButton optimizeBtn = new JButton("Optimize");
        optimizeBtn.setBounds(230, 510, 100, 30);
        optimizeBtn.addActionListener(e -> {
            optimizeNetwork();
            panel.repaint();
        });
        add(optimizeBtn);
        
        setVisible(true);
    }
    
    private void optimizeNetwork() {
        Collections.sort(edges);
        HashSet<Node> connectedNodes = new HashSet<>();
        ArrayList<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            if (!connectedNodes.contains(edge.src) || !connectedNodes.contains(edge.dest)) {
                mst.add(edge);
                connectedNodes.add(edge.src);
                connectedNodes.add(edge.dest);
            }
        }
        edges = mst;
    }
    
    public static void main(String[] args) {
        new NetworkOptimizer();
    }
}

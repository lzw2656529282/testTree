package com.lzw.tree;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: liuzhenwei
 * @date: 2020/12/11
 */

public class TestTree {
    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(new Node(0, "赋值1"));
        nodeList.add(new Node(1, "赋值2"));
        nodeList.add(new Node(2, "赋值3"));
        nodeList.add(new Node(3, "赋值4"));
        nodeList.add(new Node(4, "赋值5"));
        nodeList.add(new Node(5, "赋值6"));
        nodeList.add(new Node(6, "赋值7"));
        nodeList.add(new Node(7, "赋值8"));
        Graph graph = new Graph(nodeList.size());
        //添加节点
        for (Node node : nodeList) {
            graph.insertPoint(node);
        }
        //添加线
        graph.insertLine(new Line(0, 1, "0001"));
        graph.insertLine(new Line(1, 2, "0001"));
        graph.insertLine(new Line(2, 3, "0001"));
        graph.insertLine(new Line(2, 4, "0001"));
        graph.insertLine(new Line(2, 5, "0001"));
        graph.insertLine(new Line(3, 6, "0001"));
        graph.insertLine(new Line(4, 6, "0001"));
        graph.insertLine(new Line(6, 7, "0001"));

        //打印图的邻接表
        graph.showGraph(graph);
    }
}

@Data
class Graph {
    /**
     * 功能描述 list存储所有的顶点
     */
    ArrayList<Node> pointList;
    /**
     * 功能描述 二维数组保存所有的线
     */
    String[][] line;

    public Graph(int n) {
        //n表示节点个数
        pointList = new ArrayList<Node>(n);
        line = new String[n][n];
    }

    /**
     * 功能描述  添加节点
     *
     * @param node
     * @return void
     * @author liuzhenwei
     * @date 2020/12/11
     */

    public void insertPoint(Node node) {
        //在所有的from to 对象中获取所有的节点,或者使用前端传过来所有的节点
        pointList.add(node);
    }

    /**
     * 功能描述 添加线
     *
     * @param lines 连线对象
     * @return void
     * @author liuzhenwei
     * @date 2020/12/11
     */
    public void insertLine(Line lines) {
            line[lines.getFrom()][lines.getTo()] = lines.getWith();
    }
    /**
     *功能描述 打印图的二维数组
     * @author liuzhenwei
     * @date 2020/12/11
     * @param graph
     * @return void
     */
    public void showGraph(Graph graph) {
        String[][] line = graph.getLine();
        for (String[] strings : line) {
            System.out.println(Arrays.toString(strings));
        }
    }

}

@Data
@AllArgsConstructor
class Node {
    private int id;
    private String name;
}

@Data
@AllArgsConstructor
class Line {
    /**
     * 功能描述 线出发节点
     */
    private int from;
    /**
     * 功能描述 线到达节点
     */
    private int to;
    /**
     * 功能描述 线的权值
     */
    private String with;
}
//待修改内容   line的from和to的类型应该为node with就是树所带的条件
//哪个纵列全是0 就是开始节点

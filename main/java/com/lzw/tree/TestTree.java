package com.lzw.tree;

import cn.hutool.core.util.ReUtil;
import com.sun.org.apache.xerces.internal.xs.StringList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        Node node0 = new Node(0, "赋值1");
        Node node1 = new Node(1, "赋值2");
        Node node2 = new Node(2, "赋值3");
        Node node3 = new Node(3, "赋值4");
        Node node4 = new Node(4, "赋值5");
        Node node5 = new Node(5, "赋值6");
        Node node6 = new Node(6, "赋值7");
        Node node7 = new Node(7, "赋值8");
        nodeList.add(node0);
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
        nodeList.add(node7);
        nodeList.add(new Node(999,"sada"));
        Graph graph = new Graph(nodeList.size());
        //添加节点
        for (Node node : nodeList) {
            graph.insertPoint(node);
        }
        //添加线
        graph.insertLine(new Line(node0, node1, "0001"));
        graph.insertLine(new Line(node1, node2, "0001"));
        graph.insertLine(new Line(node2, node3, "0001"));
        graph.insertLine(new Line(node2, node4, "0001"));
        graph.insertLine(new Line(node2, node5, "0001"));
        graph.insertLine(new Line(node3, node6, "0001"));
        graph.insertLine(new Line(node4, node6, "0001"));
        graph.insertLine(new Line(node6, node7, "0001"));

        //打印图的邻接表
        graph.showGraph(graph);
        System.out.println("==============================================");
        //找到开始节点
        System.out.println(graph.getBegin(graph));
        System.out.println("==============================================");
        //判断是否存在孤立节点
        System.out.println(graph.isolated(graph.getLineList(), graph.getPointList()));
    }
}

@Data
@NoArgsConstructor
class Graph {
    /**
     * 功能描述 list存储所有的顶点
     */
    ArrayList<Node> pointList;
    /**
     * 功能描述 二维数组保存所有的线
     */
    ArrayList<Line> lineList;

    public String[][] lineToString(List<Line> initLine){
        String[][] strings = new String[initLine.size()][initLine.size()];
        for (Line init : initLine) {
            strings[init.getFrom().getId()][init.getTo().getId()]=init.getWith();
        }
        return strings;
    }

    public Graph(int n) {
        //n表示节点个数
        pointList = new ArrayList<Node>(n);
        lineList = new ArrayList<Line>();
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
        lineList.add(new Line(lines.getFrom(),lines.getTo(),lines.getWith()));
    }

    /**
     * 功能描述 打印图的二维数组
     *
     * @param graph
     * @return void
     * @author liuzhenwei
     * @date 2020/12/11
     */
    public void showGraph(Graph graph) {
        String[][] line = graph.lineToString(graph.getLineList());
        for (String[] strings : line) {
            System.out.println(Arrays.toString(strings));
        }
    }

    /**
     * 功能描述 哪个纵列全是0 就是开始节点
     *
     * @param graph
     * @return com.lzw.tree.Node
     * @author liuzhenwei
     * @date 2020/12/14
     */
    public Node getBegin(Graph graph) {
        String regex = "[null]*";
        String[][] line = graph.lineToString(graph.getLineList());
        for (int i = 0; i < line.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < line[i].length; j++) {
                //在这个循环中按照列提取数据
                stringBuilder.append(line[j][i]);
            }
            //判断该stringBuilder是否全是null
            if (ReUtil.isMatch(regex, stringBuilder.toString())) {
                //i为节点id,根据id获取节点
                return getNodeById(graph,i);
            }
        }
        return null;
    }
    /**
     *功能描述  根据id 查找节点
     * @author liuzhenwei
     * @date 2020/12/15
     * @param graph, id
     * @return com.lzw.tree.Node
     */
    public Node getNodeById(Graph graph,int id){
        ArrayList<Node> pointList = graph.getPointList();
        for (Node node : pointList) {
            if(node.getId()==id){
                return node;
            }
        }
        return  null;
    }
    /**
     *功能描述  判断是否存在孤立节点，lineList的节点全部取出放到list中，随后与nodeList的长度进行比较
     * @author liuzhenwei
     * @date 2020/12/15
     * @param lineList nodeList
     * @return boolean  false：不存在孤立节点    true：存在孤立节点
     */
    public boolean isolated(List<Line> lineList,List<Node> nodeList){
        List<Node> lineNodeList = new ArrayList<Node>();
        for (Line line1 : lineList) {
            if(!lineNodeList.contains(line1.getFrom())){
                lineNodeList.add(line1.getFrom());
            }
            if(!lineNodeList.contains(line1.getTo())){
                lineNodeList.add(line1.getTo());
            }
        }
        if(lineNodeList.size()==nodeList.size()){
            //不存在孤立节点
            return false;
        }
        return true;
    }
//一行全是0的就是结束节点
}

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Node from;
    /**
     * 功能描述 线到达节点
     */
    private Node to;
    /**
     * 功能描述 线的权值
     */
    private String with;
}


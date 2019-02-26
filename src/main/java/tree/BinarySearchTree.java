package tree;

import java.util.Collection;
import java.util.Stack;

/**
 * @program: classicalalgorithms
 * @description: 二叉搜索树，创建、插入节点、删除节点、搜索、排序、前/中/后序遍历等
 * @author: WANGYE
 * @create: 2019-$(MONTH)-24 18:12
 **/
public class BinarySearchTree {
    private Node root;

    /**
     * @description: 插入节点至二叉树
     * @Param:
     * @Return:
     * @author: WANGYE
     * @date: 2019/2/24
     * @time: 18:08
     **/
    public void insert(Comparable val) {
        Node node = new Node(val);
        if (root == null) {
            root = node;
        } else {
            insert(node, root);
        }
    }


    /**
     * @description: 将节点插入当前节点
     * @Param: node 插入节点 current 当前节点
     * @Return: void
     * @author: WANGYE
     * @date: 2019/2/24
     * @time: 18:10
     **/
    private void insert(Node node, Node current) {
        if (node.compareTo(current) <= 0) {
            if (current.getLeft() == null) {
                current.setLeft(node);
                node.setParent(current);
            } else
                insert(node, current.getLeft());
        } else {
            if (current.getRight() == null) {
                current.setRight(node);
                node.setParent(current);
            } else
                insert(node, current.getRight());
        }
    }

    /**
     * @description: 删除节点
     * @Param:
     * @Return:
     * @author: WANGYE
     * @date: 2019/2/24
     * @time: 18:55
     **/
    public void delete(Node node) {
        if (node == null) return;
        //根节点
        if (node.getParent() == null) node = null;
        switch (node.hasChilds()) {
            case 0:  // 叶节点
                if (node.isLeftChild()) node.getParent().setLeft(null);
                else node.getParent().setRight(null);
                break;
            case 1:
                // 只有单左子节点
                if (node.isLeftChild()) node.getParent().setLeft(node.getLeft());
                else node.getParent().setRight(node.getLeft());
                node.getLeft().setParent(node.getParent());
                break;
            case 2:// 只有单右子节点
                if (node.isLeftChild()) node.getParent().setLeft(node.getRight());
                else node.getParent().setRight(node.getLeft());
                node.getRight().setParent(node.getParent());
                break;
            case 3:
                // 双子节点
                Node latter = latterNode(node);
                if (node.isLeftChild())
                    node.getParent().setLeft(latter);
                else node.getParent().setRight(latter);
                if (node.getRight().equals(latter)) {
                    latter.setLeft(node.getLeft());
                    node.getLeft().setParent(latter);
                } else {
                    // latter的右子节点
                    latter.getParent().setLeft(latter.getRight());
                    if (latter.getRight() != null) latter.getRight().setParent(latter.getParent());
                    // 处理node的左子节点
                    latter.setLeft(node.getLeft());
                    node.getLeft().setParent(latter);
                    //处理node的右子节点
                    latter.setRight(node.getRight());
                    node.getRight().setParent(latter);
                }
                latter.setParent(node.getParent());
                break;
        }
    }

    /**
     * @description: 创建树
     * @Param:
     * @Return:
     * @author: WANGYE
     * @date: 2019/2/25
     * @time: 22:49
     **/
    public void createTree(Collection<Comparable> collection) {
        for (Comparable comparable : collection) {
            insert(comparable);
        }
    }


    /**
     * @description: 后继节点（右子树的最左节点）
     * @Param:
     * @Return:
     * @author: WANGYE
     * @date: 2019/2/24
     * @time: 18:32
     **/
    public Node latterNode(Node node) {
        if (node == null) return null;
        if (node.getRight() != null)
            return findLeftest(node.getRight());
        else return node;
    }

    private Node findLeftest(Node node) {
        if (node == null) return null;
        if (node.getLeft() != null) {
            node = node.getLeft();
            return findLeftest(node);
        } else return node;
    }

    /**
     * @description: 前驱节点(左子树的最右节点)
     * @Param:
     * @Return:
     * @author: WANGYE
     * @date: 2019/2/24
     * @time: 18:54
     **/
    public Node formerNode(Node node) {
        if (node == null) return null;
        if (node.getLeft() != null)
            return findrightest(node.getLeft());
        else return node;
    }

    private Node findrightest(Node node) {
        if (node == null) return null;
        if (node.getRight() != null) {
            node = node.getRight();
            return findrightest(node);
        } else return node;
    }

    /**
     * @description: 中序遍历-递归写法
     * @Param:
     * @Return:
     * @author: WANGYE
     * @date: 2019/2/25
     * @time: 22:53
     **/
    public void midSortPrint(Node node) {
        if (node == null) return;
        midSortPrint(node.left);
        System.out.println(node);
        midSortPrint(node.right);
    }

    public void midLoopSortPrint(Node node){
        Stack<Node>stack=new Stack<>();

    }

    /**
    * @description: 前序遍历-递归写法
    * @Param:
    * @Return:
    * @author: WANGYE
    * @date: 2019/2/25
    * @time: 22:54
    **/
    public void formerSortPrint(Node node){
        System.out.println(node);
        midSortPrint(node.left);
        midSortPrint(node.right);
    }

    /**
     * @description: 后序遍历-递归写法
     * @Param:
     * @Return:
     * @author: WANGYE
     * @date: 2019/2/25
     * @time: 22:54
     **/
    public void latterSortPrint(Node node){
        midSortPrint(node.left);
        midSortPrint(node.right);
        System.out.println(node);
    }








    /**
     * @description: 二叉树基本节点，保护一个可比较的值
     * @author: WANGYE
     * @date: 2019/2/24
     * @time: 18:16
     **/
    class Node implements Comparable<Node> {
        Comparable value;
        Node left;
        Node right;
        Node parent;

        public Node(Comparable value) {
            this.value = value;
        }

        public Comparable getValue() {
            return value;
        }

        public void setValue(Comparable value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        @Override
        public int compareTo(Node o) {
            return value.compareTo(o.getValue());
        }

        public boolean isLeaf() {
            if (left == right && left == null) return true;
            else return false;
        }

        public boolean isLeftChild() {
            if (parent != null) return false;
            if (this == parent.left) return true;
            return false;
        }

        public int hasChilds() {
            int code = 0;
            if (left != null) code += 1;
            if (right != null) code += 2;
            return code;
        }

        @Override
        public String toString() {
            return value.toString();
        }


    }
}

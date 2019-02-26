package old;

/**
 * @program: classicalalgorithms
 * @description: 二叉搜索树
 * @author: WANGYE
 * @create: 2019-02-26 21:44
 **/
public class BinarySearchTree<T extends Comparable> {
    Node root;

    public void insertNode(Node node, Node parent, T val, int idx) {
        if (node == null) {
            Node inode = new Node(val);
            if (parent != null) {
                node = inode;
                inode.parent = parent;
                if (idx == 0) parent.left = inode;
                else parent.right = inode;
                return;
            } else {
                root = inode;
                return;
            }
        }
        if (val.compareTo(node.value) >= 0)
            insertNode(node.left, node, val, 0);
        else
            insertNode(node.right, node, val, 1);
    }

    public void delNode(Node node) {
        // 1 个孩子的情况（可以是无孩子）
        if (node.left == null) {
            transplant(node, node.right);
        } else if (node.right == null) {
            transplant(node, node.left);
        } else {
            //两孩子的情况
            Node aftNode = treeMin(node.right);
            if (aftNode.parent != node) {
                transplant(aftNode, aftNode.right);
                aftNode.right = node.right;
                aftNode.right.parent = aftNode;
            }
            transplant(node, aftNode);
            aftNode.left = node.left;
            aftNode.left.parent = aftNode;
        }
    }

    private void transplant(Node u, Node v) {
        if (u.parent == null) root = v;
        else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) v.parent = u.parent;

    }

//    //-1 node为null 0 无child 1 left 2 right 3 left+right
//    private int getChildrenIdx(Node node){
//        if(node==null)return -1;
//        int idx=0;
//        if(node.left!=null)idx=1;
//        if(node.right!=null)idx+=2;
//        return idx;
//    }

    public Node treeMin(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    public Node treeMax(Node node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    public Node treeAftNode(Node node) {
        if (node.right != null)
            return treeMin(node.right);
        while (node.parent != null && node.parent.right == node) {
            node = node.parent;
        }
        return node.parent;
    }

    public Node treePreNode(Node node) {
        if (node.left != null)
            return treeMax(node.left);
        while (node.parent != null && node.parent.left == node) {
            node = node.parent;
        }
        return node.parent;
    }

    //找不到节点就返回null
    public Node searchNode(T val) {
        Node node = root;
        while (node.value.compareTo(val) != 0 && node != null) {
            if (node.value.compareTo(val) > 0)
                node = node.left;
            else node = node.right;
        }
        return node;
    }


    class Node {
        T value;
        Node left;
        Node right;
        Node parent;

        public Node(T value) {
            this.value = value;
        }
    }
}
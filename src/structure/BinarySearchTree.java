package structure;

import common.Token;

public class BinarySearchTree {
    private Node root;

    public BinarySearchTree() {
    }

    public Node insert(Node node, String value, Token token) {
        if (node == null) {
            return new Node(value, token);
        }
        int status = value.compareTo(node.getValue());
        if (status < 0) {
            node.setLeft(insert(node.getLeft(), value, token));
        } else if (status > 0) {
            node.setRight(insert(node.getRight(), value, token));
        } else {
            return node;
        }
        return node;
    }

    public void add(String value, Token token) {
        root = insert(root, value, token);
    }

    public void leftRootRightPrint() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node == null) {
            return;
        }

        printInOrder(node.getLeft());
        System.out.println(node.getValue() + " " + node.getToken());
        printInOrder(node.getRight());
    }
}
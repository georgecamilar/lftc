package structure;

import common.Token;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
    private Node root;
    private List<String> bstListValues;

    public BinarySearchTree() {
        bstListValues = new ArrayList<>();
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


    public void writeToFile(String filename) {
        bstToList(root);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : bstListValues) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void bstToList(Node node) {
        if (node == null) {
            return;
        }

        bstToList(node.getLeft());
        this.bstListValues.add(node.getValue() + " " + node.getToken());
        bstToList(node.getRight());
    }


}
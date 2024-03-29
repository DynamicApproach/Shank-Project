// VariableReferenceNode should contain the name of the variable being referenced.
package shank.project;
public class VariableReferenceNode extends Node {
    private String name;

    public VariableReferenceNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}


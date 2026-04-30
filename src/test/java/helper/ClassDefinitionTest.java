package helper;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public abstract class ClassDefinitionTest {

    protected abstract String getClassName();
    protected abstract List<AttributeData> getExpectedAttributes();
    protected abstract List<Class[]> getConstructorParameterTypes();
    protected abstract List<Object[]> getConstructorArguments();

    @Test
    @Order(1)
    @DisplayName("Class is defined in the models package")
    void modelsPackageContainsClass() {
        try {
            Class.forName(getClassName());
        } catch (ClassNotFoundException e) {
            throw new AssertionError("The models package should contain a class named " + getClassName());
        }
    }

    @Test
    @Order(2)
    @DisplayName("Class is defined with defined constructors")
    void classHasConstructorThatInitializesAllAttributesButId() {
        try {
            Class<?> classFromName = Class.forName(getClassName());
            ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(classFromName);
            List<Class[]> constructorParameterTypes = getConstructorParameterTypes();
            constructorParameterTypes.forEach(parameters -> {
                definitionHelper.testConstructor(parameters);
            });

        } catch (ClassNotFoundException e) {
            throw new AssertionError("The models package should contain a class named " + getClassName());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Class is defined with correct attributes")
    void classHasCorrectAttributes() {
        try {
            Class<?> classFromName = Class.forName(getClassName());
            ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(classFromName);
            definitionHelper.testAttributes(getExpectedAttributes());
        } catch (ClassNotFoundException e) {
            throw new AssertionError("The models package should contain a class named " + getClassName());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Class has getters for defined attributes")
    void classHasGettersForAllAttributes() {
        try {
            Class<?> classFromName = Class.forName(getClassName());
            ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(classFromName);
            definitionHelper.testGetters(getExpectedAttributes());
        } catch (ClassNotFoundException e) {
            throw new AssertionError("The models package should contain a class named " + getClassName());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Class has setters for defined attributes")
    void classHasSettersForAllAttributes() {
        try {
            Class<?> classFromName = Class.forName(getClassName());
            ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(classFromName);
            definitionHelper.testSetters(getExpectedAttributes().subList(1, getExpectedAttributes().size()));
        } catch (ClassNotFoundException e) {
            throw new AssertionError("The models package should contain a class named " + getClassName());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Class has toString method")
    void classHasToStringMethod() {
        try {
            Class<?> classFromName = Class.forName(getClassName());
            ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(classFromName);
            definitionHelper.testToStringMethodExists();
        } catch (ClassNotFoundException e) {
            throw new AssertionError("The models package should contain a class named " + getClassName());
        }
    }

    @Test
    @Order(7)
    @DisplayName("Class toString has appropriate format")
    void classHasToStringMethodFormat() {
        try {
            Class<?> classFromName = Class.forName(getClassName());
            ClassDefinitionHelper definitionHelper = new ClassDefinitionHelper(classFromName);

            Class[] constructorParameterTypes = getConstructorParameterTypes().get(0);
            Object[] constructorArguments = getConstructorArguments().get(0);
            // Regex that matches the format of the toString method
            // <Atributo1>: <Valor1> - <Atributo2>: <Valor2> ... <AtributoN>: <ValorN>
            String regex = getExpectedAttributes().stream()
                .map(attribute ->
                {   String attributeNameWithFirstLetterUpperCase =
                        attribute.name().substring(0, 1).toUpperCase() + attribute.name().substring(1);
                    return attributeNameWithFirstLetterUpperCase + ": .*";})
                .reduce((a, b) -> a + " - " + b)
                .orElse("");

            definitionHelper.testToStringMethodHasAppropriateFormat(
                constructorParameterTypes,
                constructorArguments,
                regex);
        } catch (ClassNotFoundException e) {
            throw new AssertionError("The models package should contain a class named " + getClassName());
        }
    }
}
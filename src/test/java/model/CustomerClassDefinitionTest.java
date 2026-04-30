package model;

import helper.AttributeData;
import helper.ClassDefinitionHelper;
import helper.ClassDefinitionTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomerClassDefinitionTest extends ClassDefinitionTest {

    @Override
    protected String getClassName() {
        return "com.javeriana.model.Customer";
    }

    @Override
    protected List<AttributeData> getExpectedAttributes() {
        return Arrays.asList(
            new AttributeData("id", "UUID", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("username", "String", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("password", "String", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("name", "String", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("lastName", "String", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("age", "int", ClassDefinitionHelper.PRIVATE_MODIFIER)
        );
    }

    @Override
    protected List<Class[]> getConstructorParameterTypes() {
        Class[] parametersConstructor1 = new Class[]{String.class, String.class, String.class, String.class, int.class};
        return new ArrayList<>(Collections.singleton(parametersConstructor1));
    }

    @Override
    protected List<Object[]> getConstructorArguments() {
        Object[] constructor1 = new Object[]{"username", "password", "name", "lastName", 25};
        return new ArrayList<>(Collections.singleton(constructor1));
    }


}
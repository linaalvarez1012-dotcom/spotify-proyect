package model;

import helper.AttributeData;
import helper.ClassDefinitionHelper;
import helper.ClassDefinitionTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SongClassDefinitionTest extends ClassDefinitionTest {

    @Override
    protected String getClassName() {
        return "com.javeriana.model.Song";
    }

    @Override
    protected List<AttributeData> getExpectedAttributes() {
        return Arrays.asList(
            new AttributeData("id", "UUID", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("name", "String", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("genre", "String", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("durationInSeconds", "int", ClassDefinitionHelper.PRIVATE_MODIFIER),
            new AttributeData("album", "String", ClassDefinitionHelper.PRIVATE_MODIFIER)
        );
    }

    @Override
    protected List<Class[]> getConstructorParameterTypes() {
        return new ArrayList<>(Collections.singleton(new Class[]{String.class, String.class, int.class, String.class}));
    }

    @Override
    protected List<Object[]> getConstructorArguments() {
        return new ArrayList<>(Collections.singleton(new Object[]{"Song Name", "Rock", 180, "Album Name"}));
    }
}

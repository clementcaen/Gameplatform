package fr.isencaen.gameplatform.filter;


import fr.isencaen.gameplatform.exceptions.IllegalClassException;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.HashSet;
import java.util.Set;

public class WhitelistedObjectInputStream extends ObjectInputStream {

    private static final Set<String> WHITELIST = new HashSet<>();

    static {
        WHITELIST.add("fr.isencaen.gameplatform.models.Account");
        WHITELIST.add("fr.isencaen.gameplatform.models.AccountPosition");
        WHITELIST.add("fr.isencaen.gameplatform.models.CurrentGame");
        WHITELIST.add("fr.isencaen.gameplatform.models.Game");
        WHITELIST.add("fr.isencaen.gameplatform.models.PositionsObject");
        WHITELIST.add("java.util.ArrayList");
        WHITELIST.add("org.hibernate.collection.spi.PersistentBag");
        WHITELIST.add("org.hibernate.collection.spi.AbstractPersistentCollection");
        WHITELIST.add("java.lang.Integer");
        WHITELIST.add("java.lang.Number");
    }

    public WhitelistedObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @SneakyThrows
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String className = desc.getName();
        if (!WHITELIST.contains(className)) {
            throw new IllegalClassException("Deserialization of class " + className + " is not allowed.");
        }
        return super.resolveClass(desc);
    }
}

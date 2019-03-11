package cl.queltehues.dummies;

import org.aspectj.lang.Signature;

/**
 * El propósito de esta clase para poder maquetear "getSignature()"
 */
public class DummySignature implements Signature {

    @Override
    public String toShortString() {
        return "shortString";
    }

    @Override
    public String toLongString() {
        return "longString";
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public int getModifiers() {
        return 0;
    }

    @Override
    public Class getDeclaringType() {
        return null;
    }

    @Override
    public String getDeclaringTypeName() {
        return null;
    }
}
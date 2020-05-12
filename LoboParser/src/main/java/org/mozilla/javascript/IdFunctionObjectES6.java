package org.mozilla.javascript;

/**
 * <p>IdFunctionObjectES6 class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class IdFunctionObjectES6 extends IdFunctionObject{

    private static final long serialVersionUID = -8023088662589035261L;

    /**
     * <p>Constructor for IdFunctionObjectES6.</p>
     *
     * @param idcall a {@link org.mozilla.javascript.IdFunctionCall} object.
     * @param tag a {@link java.lang.Object} object.
     * @param id a int.
     * @param name a {@link java.lang.String} object.
     * @param arity a int.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     */
    public IdFunctionObjectES6(IdFunctionCall idcall, Object tag, int id, String name, int arity, Scriptable scope) {
        super(idcall, tag, id, name, arity, scope);
    }

    private static final int Id_length = 1, Id_name = 3;
    private boolean myLength = true;
    private boolean myName = true;

    /** {@inheritDoc} */
    @Override
    protected int findInstanceIdInfo(String s) {
        if (s.equals("length")) return instanceIdInfo(READONLY | DONTENUM, Id_length);
        else if (s.equals("name")) return instanceIdInfo(READONLY | DONTENUM, Id_name);
        return super.findInstanceIdInfo(s);
    }

    /** {@inheritDoc} */
    @Override
    protected Object getInstanceIdValue(int id) {
        if (id == Id_length && !myLength) {
            return NOT_FOUND;
        } else if (id == Id_name && !myName) {
            return NOT_FOUND;
        }
        return super.getInstanceIdValue(id);
    }

    /** {@inheritDoc} */
    @Override
    protected void setInstanceIdValue(int id, Object value) {
        if (id == Id_length && value == NOT_FOUND) {
            this.myLength = false;
            return;
        } else if (id == Id_name && value == NOT_FOUND) {
            this.myName = false;
            return;
        }
        super.setInstanceIdValue(id, value);
    }
}

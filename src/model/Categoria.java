package src.model;

import src.dao.CategoriaDao;
import src.model.abstractmodel.AbstractCategoria;

import java.util.HashSet;

public class Categoria extends AbstractCategoria implements IPersistable {

    protected HashSet<Producto> categorizados = new HashSet<>();

    public Categoria(String categoria) {
        super(categoria);
    }

    public Categoria(int idCategoria, String categoria) {
        super(idCategoria, categoria);
    }

    @Override
    public int updateOnDb() {
        return CategoriaDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return CategoriaDao.getInstance().updateDao(this);
    }
}

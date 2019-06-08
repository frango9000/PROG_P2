package src.model;

import src.dao.CategoriasDao;
import src.model.abstractmodel.AbstractCategoria;

import java.util.ArrayList;

public class Categoria extends AbstractCategoria implements IPersistable {

    private ArrayList<Producto> categorizados = new ArrayList<>();

    public Categoria(String categoria) {
        super(categoria);
    }

    public Categoria(int idCategoria, String categoria) {
        super(idCategoria, categoria);
    }

    public ArrayList<Producto> getCategorizados() {
        return categorizados;
    }

    @Override
    public int updateOnDb() {
        return CategoriasDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return CategoriasDao.getInstance().updateDao(this);
    }

}

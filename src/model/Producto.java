package src.model;

import src.dao.CategoriasDao;
import src.dao.ProductosDao;
import src.model.abstractmodel.AbstractProducto;

public class Producto extends AbstractProducto implements IPersistable {

    public Producto(int id, String producto, float precio, int idCategoria) {
        super(id, producto, precio, idCategoria);
        refreshCategoria();
    }

    public Producto(String producto, float precio, int idCategoria) {
        super(producto, precio, idCategoria);
        refreshCategoria();
    }

    @Override
    public void setIdCategoria(int idCategoria) {
        super.setIdCategoria(idCategoria);
        refreshCategoria();
    }

    @Override
    public int updateOnDb() {
        return ProductosDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return ProductosDao.getInstance().updateDao(this);
    }

    private void refreshCategoria() {
        if (idCategoria > 0)
            categoria = CategoriasDao.getInstance().get(idCategoria);
        else categoria = null;
    }
}

package src.model;

import src.dao.ProductosDao;
import src.dao.ServidoDao;
import src.model.abstractmodel.AbstractServido;

public class Servido extends AbstractServido implements IPersistable {

    public Servido(int id, int idOrden, int idProducto) {
        super(id, idOrden, idProducto);
        refreshProducto();
    }

    public Servido(int idOrden, Producto producto) {
        super(idOrden, producto);
    }

    public Servido(int idOrden, int idProducto) {
        super(idOrden, idProducto);
    }

    @Override
    public void setIdProducto(int idProducto) {
        super.setIdProducto(idProducto);
        refreshProducto();
    }

    @Override
    public int updateOnDb() {
        return ServidoDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return ServidoDao.getInstance().updateDao(this);
    }

    private void refreshProducto() {
        producto = ProductosDao.getInstance().get(idProducto);
    }
}

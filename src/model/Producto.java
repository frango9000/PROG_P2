package src.model;

import src.dao.CategoriasDao;
import src.dao.ProductosDao;
import src.model.abstractmodel.AbstractProducto;

public class Producto extends AbstractProducto implements IPersistable {

    private Categoria categoria;

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
        categoria.getCategorizados().remove(this);
        super.setIdCategoria(idCategoria);
        refreshCategoria();
    }

    public void setCategoria(Categoria categoria) {
        categoria.getCategorizados().remove(this);
        this.categoria = categoria;
        this.idCategoria = categoria.getId();
        categoria.getCategorizados().add(this);
    }

    public Categoria getCategoria() {
        return categoria;
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
        if (idCategoria > 0) {
            categoria = CategoriasDao.getInstance().get(idCategoria);
            categoria.getCategorizados().add(this);
        } else {
            categoria = null;
        }
    }
}

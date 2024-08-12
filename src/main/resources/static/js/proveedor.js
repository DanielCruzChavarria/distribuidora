 document.addEventListener("DOMContentLoaded", function () {
    const editButtons = document.querySelectorAll('.edit-btn');
    const deleteButtons = document.querySelectorAll('[data-bs-target="#deleteModal"]');
    const proveedorForm = document.getElementById('proveedorForm');
    const proveedorModal = new bootstrap.Modal(document.getElementById('proveedorModal'));

    editButtons.forEach(button => {
        button.addEventListener('click', function (event) {
        alert("CALLING")
            event.preventDefault();
            const proveedorId = this.getAttribute('data-proveedor-id');
            const proveedorProductoId = this.getAttribute('data-id');
            const nombre = this.getAttribute('data-nombre');
            const clave = this.getAttribute('data-clave');
            const costo = this.getAttribute('data-costo');

            document.getElementById('proveedorId').value = proveedorId;
            document.getElementById('proveedorProductoId').value = proveedorProductoId;
            document.getElementById('claveProveedor').value = clave;
            document.getElementById('costo').value = costo;

            proveedorModal.show();
        });
    });
        const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
        const deleteForm = document.getElementById('deleteForm');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            alert("CLick");

            const productId = this.getAttribute('data-product-id');
            console.log("Si hay producto", productId);
            if(productId){
                deleteForm.action = `/proveedorProducto/delete/${productId}`;
                deleteModal.show();
            }
        });
    });






});



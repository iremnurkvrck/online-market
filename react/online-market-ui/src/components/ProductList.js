import React, { useState, useEffect } from "react";
import axios from "axios";
import ProductView from "./ProductView"; // ProductView bileşenini import ettik

import "./Product.css"; // CSS dosyasını import edin

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [quantities, setQuantities] = useState({}); // Her ürün için miktarları tutacak state

  useEffect(() => {
    // Backend'den ürünleri almak için API çağrısı yapalım
    axios
      .get("http://localhost:8080/api/v1/auth/all")
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
      });
  }, []);

  // Kullanıcının bir ürün için miktarını değiştirdiğinde çalışacak fonksiyon
  const handleQuantityChange = (productId, event) => {
    const value = Math.max(1, event.target.value); // Miktar 1'den küçük olamaz
    setQuantities((prevQuantities) => ({
      ...prevQuantities,
      [productId]: value,
    }));
  };

  return (
    <div className="product-list">
      <h1 className="title">Ürünler</h1>
      {/* ProductView bileşenini burada kullanıyoruz ve gerekli props'ları geçiriyoruz */}
      <ProductView
        products={products}
        quantities={quantities}
        handleQuantityChange={handleQuantityChange}
      />
    </div>
  );
};

export default ProductList;

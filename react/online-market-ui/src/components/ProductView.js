import React, { useState } from "react";
import { Link } from "react-router-dom";
import defaultImage from "../assets/default-image.png";
import { addToCart } from "./CartService"; // CartService dosyasını import edin
import { addToFavorites } from "./AddFavorite"; // Favorilere ekleme fonksiyonunu import edin
import "./Product.css";

const ProductView = ({ products, userId }) => {
  const [quantities, setQuantities] = useState({});

  // Miktar değişikliği işlemi
  const handleQuantityChange = (productId, event) => {
    const newQuantity = event.target.value;
    setQuantities((prevQuantities) => ({
      ...prevQuantities,
      [productId]: newQuantity,
    }));
  };

  // Sepete ekleme işlemi
  const handleAddToCart = async (product) => {
    const cartData = {
      userId, // Kullanıcı ID'si
      pid: product.id, // Ürün ID'si
      quantity: quantities[product.id] || 1, // Miktar bilgisi
      image: product.image || "default-image.png", // Varsayılan resim
    };

    try {
      const cartResult = await addToCart(cartData);
      if (cartResult.success) {
        alert(`${product.name} sepete başarıyla eklendi!`);
      } else {
        alert("Ürün sepete eklenirken bir hata oluştu!");
      }
    } catch (error) {
      alert("Bir hata oluştu! Lütfen tekrar deneyin.");
      console.error(error);
    }
  };

  // Favorilere ekleme işlemi
  const handleAddToFavorites = async (product) => {
    const favoriteData = {
      userId,
      pid: product.id, // Ürün ID'si
      image: product.image || "default-image.png", // Varsayılan resim
      productName: product.name, // Ürün adı
    };

    try {
      const favoriteResult = await addToFavorites(favoriteData);
      if (favoriteResult.success) {
        alert(`${product.name} favorilere başarıyla eklendi!`);
      } else {
        alert("Ürün favorilere eklenirken bir hata oluştu!");
      }
    } catch (error) {
      alert("Bir hata oluştu! Lütfen tekrar deneyin.");
      console.error(error);
    }
  };

  return (
    <div className="product-list">
      <h1 className="title">Ürünler</h1>
      <div className="products-box-container">
        {products.length > 0 ? (
          products.map((product) => {
            const productImage = product.image
              ? require(`../assets/${product.image}`)
              : defaultImage;

            return (
              <div key={product.id} className="box">
                <img src={productImage} alt={product.name} />

                <Link
                  to="/wishlist"
                  className="bi bi-eye"
                  state={{ product }}
                ></Link>

                <h3 className="name">{product.name}</h3>
                <div className="price">{product.price} TL</div>

                <div className="quantity-input">
                  <label htmlFor={`quantity-${product.id}`}>Miktar:</label>
                  <input
                    type="number"
                    id={`quantity-${product.id}`}
                    min="1"
                    value={quantities[product.id] || 1}
                    onChange={(event) =>
                      handleQuantityChange(product.id, event)
                    }
                    className="qty"
                  />
                </div>

                {/* Favorilere Ekle butonuna basıldığında sadece favorilere eklenir */}
                <button
                  className="option-btn"
                  onClick={() => handleAddToFavorites(product)}
                >
                  Favorilere Ekle
                </button>

                {/* Sepete Ekle butonuna basıldığında sadece sepete eklenir */}
                <button
                  className="btn"
                  onClick={() => handleAddToCart(product)}
                >
                  Sepete Ekle
                </button>
              </div>
            );
          })
        ) : (
          <p>Ürün Bulunamadı!</p>
        )}
      </div>
    </div>
  );
};

export default ProductView;

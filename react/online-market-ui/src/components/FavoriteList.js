import React, { useEffect, useState } from "react";
import { addToCart } from "./CartService"; // CartService dosyasını import edin
import "./ShowCart.css";

const FavoriteList = () => {
  const [cartItems, setCartItems] = useState([]); // Sepetteki ürünler
  const [error, setError] = useState(null); // Hata durumu
  const [loading, setLoading] = useState(true); // Yükleniyor durumu
  const [totalAmount, setTotalAmount] = useState(0); // Toplam sepet tutarı
  const [quantities, setQuantities] = useState({}); // Miktarları yönetmek için

  const username = localStorage.getItem("username"); // Kullanıcı adı, login sonrası

  // API'den veri al
  useEffect(() => {
    const fetchCartItems = async () => {
      if (!username) {
        setError("Kullanıcı adı bulunamadı.");
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/auth/favorite-list/${username}`
        );
        if (!response.ok) {
          throw new Error("Favori ürünler bulunamadı!");
        }
        const data = await response.json();
        console.log("Favori ürünler:", data);

        // Aynı ürünleri gruplama ve miktarları toplama
        const groupedItems = data.reduce((acc, item) => {
          const existingItem = acc.find((i) => i.pid === item.pid);
          if (existingItem) {
            existingItem.quantity += item.quantity; // Miktarları topluyoruz
          } else {
            acc.push({ ...item });
          }
          return acc;
        }, []);

        setCartItems(groupedItems);
        setLoading(false);
      } catch (err) {
        setError("Bir hata oluştu: " + err.message);
        setLoading(false);
      }
    };

    fetchCartItems();
  }, [username]);

  const handleQuantityChange = (productId, event) => {
    const newQuantities = { ...quantities, [productId]: event.target.value };
    setQuantities(newQuantities);
  };

  const handleAddToCart = async (product) => {
    const userId = localStorage.getItem("userId"); // Kullanıcı ID'sini localStorage'dan alıyoruz
    if (!userId) {
      alert("Kullanıcı bilgisi bulunamadı.");
      return;
    }

    const cartData = {
      userId, // Kullanıcı ID'si
      pid: product.id, // Ürün ID'si
      price: product.price,
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

  if (loading) return <p>Yükleniyor...</p>;
  if (error) return <p>Hata: {error}</p>;

  return (
    <div className="shopping-cart">
      <h2>Favori Listesi</h2>
      {cartItems.length > 0 ? (
        <>
          {/* Ürünler ve toplam miktar gösterimi */}
          <div className="product-list">
            <h1 className="title">Ürünler</h1>
            <div className="products-box-container">
              {cartItems.map((product) => {
                const productImage = product.image
                  ? require(`../assets/${product.image}`)
                  : require("../assets/default-image.png");

                return (
                  <div key={product.pid} className="box">
                    <img src={productImage} alt={product.name} />
                    <h3 className="name">{product.name}</h3>
                    <div className="price">
                      {product.price && product.price > 0
                        ? `${product.price} TL`
                        : "Fiyat bilgisi mevcut değil"}
                    </div>

                    <div className="quantity-input">
                      <label htmlFor={`quantity-${product.pid}`}>Miktar:</label>
                      <input
                        type="number"
                        id={`quantity-${product.pid}`}
                        min="1"
                        value={quantities[product.pid] || product.quantity || 1}
                        onChange={(event) =>
                          handleQuantityChange(product.pid, event)
                        }
                        className="qty"
                      />
                      <button
                        className="btn"
                        onClick={() => handleAddToCart(product)}
                      >
                        Sepete Ekle
                      </button>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        </>
      ) : (
        <p>Favori listeniz boş.</p>
      )}
    </div>
  );
};

export default FavoriteList;

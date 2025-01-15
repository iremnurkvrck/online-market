import React, { useEffect, useState } from "react";
import "./ShowCart.css";
import { useNavigate } from "react-router-dom";

const ShoppingCart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [totalAmount, setTotalAmount] = useState(0);
  const [newProductId, setNewProductId] = useState("");
  const [newProductQuantity, setNewProductQuantity] = useState(1);

  const username = localStorage.getItem("username");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCartItems = async () => {
      if (!username) {
        setError("Kullanıcı adı bulunamadı.");
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/auth/user-cart/${username}`
        );
        if (!response.ok) {
          throw new Error("Sepet bulunamadı!");
        }
        const data = await response.json();

        const groupedItems = data.reduce((acc, item) => {
          const existingItem = acc.find((i) => i.pid === item.pid);
          if (existingItem) {
            existingItem.quantity += item.quantity;
          } else {
            acc.push({ ...item });
          }
          return acc;
        }, []);

        setCartItems(groupedItems);

        const total = groupedItems.reduce(
          (acc, item) => acc + item.price * item.quantity,
          0
        );
        setTotalAmount(total);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCartItems();
  }, [username]);

  const removeItemFromCart = async (productId) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/auth/delete-orders/${productId}`,
        {
          method: "DELETE",
        }
      );

      if (!response.ok) {
        throw new Error("Ürün silinemedi!");
      }

      setCartItems((prevItems) =>
        prevItems.filter((item) => item.pid !== productId)
      );

      const newTotal = cartItems
        .filter((item) => item.pid !== productId)
        .reduce((acc, item) => acc + item.price * item.quantity, 0);
      setTotalAmount(newTotal);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleCheckout = () => {
    navigate("/orders", { state: { cartItems } });
  };

  if (loading) return <p>Yükleniyor...</p>;
  if (error) return <p>Hata: {error}</p>;

  return (
    <div className="shopping-cart">
      <h2>Alışveriş Sepeti</h2>
      {cartItems.length > 0 ? (
        <>
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
                    <div className="price">{product.price} TL</div>

                    <div className="quantity-input">
                      <label htmlFor={`quantity-${product.pid}`}>Miktar:</label>
                      <input
                        type="number"
                        id={`quantity-${product.pid}`}
                        min="1"
                        value={product.quantity || 1}
                        readOnly
                        className="qty"
                      />
                    </div>

                    {/* Çarpı Butonu */}
                    <button
                      className="remove-btn"
                      onClick={() => removeItemFromCart(product.pid)}
                    >
                      &#10005;
                    </button>
                  </div>
                );
              })}
            </div>
          </div>

          <div className="total">
            <h3 className="total-amount">
              Toplam Sepet Tutarı: {totalAmount} TL
            </h3>
          </div>
        </>
      ) : (
        <p>Sepetiniz boş.</p>
      )}

      <button className="btn" id="onay" onClick={handleCheckout}>
        Sepeti Onayla
      </button>
    </div>
  );
};

export default ShoppingCart;

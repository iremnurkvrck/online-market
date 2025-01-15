import React, { useEffect, useState } from "react";
import "./OrderList.css";
const OrdersList = () => {
  const [orders, setOrders] = useState([]); // Siparişler
  const [error, setError] = useState(null); // Hata durumu
  const [loading, setLoading] = useState(true); // Yükleniyor durumu

  const username = localStorage.getItem("username"); // Kullanıcı adı, login sonrası

  // API'den siparişleri al
  useEffect(() => {
    const fetchOrders = async () => {
      if (!username) {
        setError("Kullanıcı adı bulunamadı.");
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/auth/orders/username/${username}`
        );
        if (!response.ok) {
          throw new Error("Siparişler bulunamadı!");
        }
        const data = await response.json();
        console.log("Siparişler:", data);
        setOrders(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, [username]);

  if (loading) return <p>Yükleniyor...</p>;
  if (error) return <p>Hata: {error}</p>;

  return (
    <div className="orders-list">
      <h2>{username} için Siparişler</h2>
      {orders.length > 0 ? (
        <div className="orders-container">
          {orders.map((order) => (
            <div key={order.id} className="order-item">
              <p>Order ID: {order.id}</p>
              <p>Telefon: {order.phoneNumber}</p>
              <p>Email: {order.email}</p>
              <p>Adres: {order.address}</p>
              <p>Metod: {order.method}</p>
              <p>Toplam Ürün: {order.totalProducts}</p>
              <p>Toplam Fiyat: {order.totalPrice} TL</p>
              <p>Durum: {order.status}</p>
              <p>Tarih: {new Date(order.placedOn).toLocaleString()}</p>
            </div>
          ))}
        </div>
      ) : (
        <p>{username} için herhangi bir sipariş bulunamadı.</p>
      )}
    </div>
  );
};

export default OrdersList;

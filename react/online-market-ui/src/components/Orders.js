import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "./Orders.css";

const Orders = () => {
  const location = useLocation();
  const cartItems = location.state?.cartItems || []; // Sepetten gelen ürünler
  const totalAmount = cartItems.reduce(
    (acc, item) => acc + item.price * item.quantity,
    0
  ); // Toplam sepet tutarı hesaplama

  // Kullanıcı bilgilerini başlangıçta boş alıyoruz
  const [userInfo, setUserInfo] = useState({
    user_id: "", // Kullanıcı ID'si
    username: "", // Kullanıcı adı
    phoneNumber: "", // Telefon numarası
    email: "", // E-posta adresi
    method: "", // Ödeme yöntemi
    address: "", // Teslimat adresi
    totalProducts: cartItems.reduce((acc, item) => acc + item.quantity, 0), // Toplam ürün sayısı
    totalPrice: totalAmount, // Toplam fiyat
    placed_on: new Date().toISOString(), // Sipariş tarihi
    status: "", // Sipariş durumu
  });

  useEffect(() => {
    // Kullanıcı bilgilerini localStorage'dan alıyoruz
    const userData = JSON.parse(localStorage.getItem("userInfo")); // Burada kullanıcı bilgilerini localStorage'dan alıyoruz

    if (userData) {
      setUserInfo((prevInfo) => ({
        ...prevInfo,
        username: userData.username, // localStorage'dan alınan kullanıcı adı
      }));
    }
  }, []);

  // Kullanıcı bilgilerini güncelleyen fonksiyon
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserInfo((prevInfo) => ({
      ...prevInfo,
      [name]: value,
    }));
  };

  // Siparişi gönderme fonksiyonu
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Backend API'sine POST isteği gönderiyoruz
      const response = await fetch(
        "http://localhost:8080/api/v1/auth/orders-create",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(userInfo),
        }
      );

      if (!response.ok) {
        throw new Error("Sipariş oluşturulurken bir hata oluştu.");
      }

      const data = await response.json();
      alert("Sipariş başarılı! Sipariş ID: " + data.id);
    } catch (error) {
      alert("Bir hata oluştu: " + error.message);
    }
  };

  if (cartItems.length === 0) {
    return <p>Sepetiniz boş. Sipariş oluşturulamıyor.</p>;
  }

  return (
    <div className="box">
      <h2>Sipariş Detayları</h2>

      <div className="order-box">
        <p>
          Toplam Ürün Sayısı:{" "}
          <span>{cartItems.reduce((acc, item) => acc + item.quantity, 0)}</span>
        </p>
        <p>
          Toplam Tutar: <span>{totalAmount.toFixed(2)} TL</span>
        </p>
      </div>

      <h3>Kullanıcı Bilgilerini Girin</h3>
      <form onSubmit={handleSubmit}>
        <div className="order-box">
          <label htmlFor="username">Kullanıcı Adı:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={userInfo.username}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="order-box">
          <label htmlFor="phoneNumber">Telefon Numaranız:</label>
          <input
            type="text"
            id="phoneNumber"
            name="phoneNumber"
            value={userInfo.phoneNumber}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="order-box">
          <label htmlFor="email">E-posta Adresiniz:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={userInfo.email}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="order-box">
          <label htmlFor="address">Teslimat Adresi:</label>
          <input
            type="text"
            id="address"
            name="address"
            value={userInfo.address}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="order-box">
          <label htmlFor="method">Ödeme Yöntemi:</label>
          <select
            id="method"
            name="method"
            value={userInfo.method}
            onChange={handleInputChange}
            required
          >
            <option value="Credit Card">Kredi Kartı</option>
            <option value="PayPal">PayPal</option>
            <option value="Bank Transfer">Banka Transferi</option>
          </select>
        </div>
        <div className="order-box">
          <label htmlFor="status">Sipariş Durumu:</label>
          <select
            id="status"
            name="status"
            value={userInfo.status}
            onChange={handleInputChange}
            required
          >
            <option value="pending">Bekliyor</option>
            <option value="completed">Tamamlandı</option>
          </select>
        </div>

        <button type="submit">Siparişi Onayla</button>
      </form>
    </div>
  );
};

export default Orders;

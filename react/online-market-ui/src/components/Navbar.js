import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Navbar.css";

const Navbar = () => {
  const navigate = useNavigate();
  const username = localStorage.getItem("username"); // Kullanıcı adını localStorage'dan alıyoruz

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("username");
    navigate("/login");
  };

  return (
    <header className="header">
      <div className="flex">
        <h1 className="logo">Online Market</h1>
        <nav className="navbar">
          <a href="/">Anasayfa</a>
          <Link to="/products">Ürünler</Link>
          <Link to="/ordersList">Siparişler</Link>
          <Link to="/about">Hakkımızda</Link>
          <Link to="/contact">İletişim</Link>

          {username ? (
            <>
              {/* Giriş yaptıysa profil linki ve çıkış yap butonu göster */}
              <Link to="/profile" className="bi bi-person">
                {username}
              </Link>
            </>
          ) : (
            // Giriş yapmamışsa giriş yap linki göster
            <Link to="/login" className="bi bi-person">
              Giriş Yap
            </Link>
          )}

          <Link to="/shoppingCart" className="bi bi-cart3"></Link>
          <Link to="/favoriteList" className="bi bi-heart"></Link>
        </nav>
      </div>
    </header>
  );
};

export default Navbar;

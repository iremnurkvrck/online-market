// src/components/LoginPage.js
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const loginRequest = {
      username: username,
      password: password,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/authenticate", // Giriş API endpoint'i
        loginRequest
      );

      // Eğer giriş başarılıysa
      if (response.status === 200) {
        const { accessToken, refreshToken } = response.data;
        localStorage.setItem("accessToken", accessToken); // Token'ı localStorage'a kaydediyoruz
        localStorage.setItem("refreshToken", refreshToken);
        localStorage.setItem("username", username); // Kullanıcı adını da kaydediyoruz

        navigate("/"); // Ana sayfaya yönlendiriyoruz
      }
    } catch (error) {
      if (error.response) {
        setErrorMessage(
          `Hata: ${
            error.response.data.message || "Geçersiz kullanıcı adı veya şifre."
          }`
        );
      } else if (error.request) {
        setErrorMessage("Sunucuya istek gönderilirken bir sorun oluştu.");
      } else {
        setErrorMessage("Bir hata oluştu: " + error.message);
      }
    }
  };

  return (
    <div className="login-container">
      <h2>Alıveriş Kapında</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Kullanıcı Adı</label>
          <input
            type="text"
            placeholder="Kullanıcı Adınızı giriniz.."
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Şifre</label>
          <input
            type="password"
            placeholder="Şifrenizi giriniz.."
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        {errorMessage && <p className="error">{errorMessage}</p>}
        <button type="submit">Giriş Yap</button>
        <div className="register">
          <span>Bir hesabınız yok mu? </span>
          <span className="register-link" onClick={() => navigate("/register")}>
            Şimdi Kaydolun
          </span>
        </div>
      </form>
    </div>
  );
};

export default LoginPage;

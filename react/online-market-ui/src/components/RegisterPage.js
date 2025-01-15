import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Register.css";

const RegisterPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [mobileNumber, setMobileNumber] = useState("");
  const [gender, setGender] = useState("");
  const [birthdate, setBirthdate] = useState("");
  const [birthplace, setBirthplace] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    const registerRequest = {
      username,
      password,
      firstName,
      lastName,
      email,
      mobileNumber, // Backend'in bu alanı doğru şekilde işlediğinden emin olun.
      gender,
      birthdate,
      birthplace,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/create-users",
        registerRequest,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.status === 201) {
        navigate("/Login");
      }
    } catch (error) {
      if (error.response) {
        setErrorMessage(
          error.response.data.message ||
            "Bir hata oluştu. Lütfen tekrar deneyin."
        );
        console.error("Backend Hatası:", error.response.data);
      } else if (error.request) {
        setErrorMessage("Sunucuya bağlanılamadı.");
        console.error("Request Hatası:", error.request);
      } else {
        setErrorMessage("Bir hata oluştu. Lütfen tekrar deneyin.");
        console.error("Hata:", error.message);
      }
    }
  };

  return (
    <div className="register-container">
      <h2>Şimdi Kaydol</h2>
      <form onSubmit={handleRegister}>
        <div>
          <label>Kullanıcı adı:</label>
          <input
            type="text"
            placeholder="Kullanıcı adınızı giriniz.."
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Şifre:</label>
          <input
            type="password"
            placeholder="Şifrenizi giriniz.."
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <label>İsim:</label>
          <input
            type="text"
            placeholder="Adınızı giriniz.."
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Soyisim:</label>
          <input
            type="text"
            placeholder="Soyadınızı giriniz.."
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Email:</label>
          <input
            type="email"
            placeholder="Email Adresinizi giriniz.."
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Telefon Numarası:</label>
          <input
            type="text"
            placeholder="Telefon numaranızı giriniz.."
            value={mobileNumber}
            onChange={(e) => setMobileNumber(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Cinsiyet:</label>
          <input
            type="text"
            placeholder="Cinsiyetinizi giriniz.."
            value={gender}
            onChange={(e) => setGender(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Doğum Tarihi:</label>
          <input
            type="date"
            placeholder="Doğum tarihinizi giriniz.."
            value={birthdate}
            onChange={(e) => setBirthdate(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Doğum Yeri:</label>
          <input
            type="text"
            placeholder="Doğum yerinizi giriniz.."
            value={birthplace}
            onChange={(e) => setBirthplace(e.target.value)}
            required
          />
        </div>
        {errorMessage && <p className="error">{errorMessage}</p>}
        <button type="submit">Kaydol</button>
      </form>
    </div>
  );
};

export default RegisterPage;

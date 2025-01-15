import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./Profile.css"; // Profil sayfası stil dosyası

const Profile = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    birthdate: "",
    birthplace: "",
    gender: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const [message, setMessage] = useState("");

  const username = localStorage.getItem("username"); // Kullanıcı adı, login sonrası

  // Kullanıcı verilerini al
  useEffect(() => {
    if (!username) {
      navigate("/login");
    } else {
      axios
        .get(`http://localhost:8080/api/v1/auth/username/${username}`)
        .then((response) => {
          setUserData(response.data);
        })
        .catch((error) => {
          console.error("Error fetching user data", error);
          setErrorMessage("Kullanıcı verileri alınırken bir hata oluştu.");
        });
    }
  }, [username, navigate]);

  // Profil güncelleme işlemi
  const handleProfileUpdate = async (e) => {
    e.preventDefault();

    const updatedUser = {
      firstName: userData.firstName,
      lastName: userData.lastName,
      email: userData.email,
      birthdate: userData.birthdate,
      birthplace: userData.birthplace,
      gender: userData.gender,
    };

    try {
      console.log("Updated User Data:", updatedUser); // Güncellenen kullanıcı verilerini kontrol edin
      const response = await axios.put(
        `http://localhost:8080/api/v1/auth/${username}`,
        updatedUser,
        {
          headers: {
            "Content-Type": "application/json", // Content-Type header'ı yeterli olacak
          },
        }
      );
      console.log("Response:", response); // Yanıtı kontrol edin
      setMessage("Profil başarıyla güncellendi.");
    } catch (error) {
      console.error("Error updating profile:", error); // Detaylı hata çıktısını kontrol edin
      if (error.response) {
        setErrorMessage(`Backend Hatası: ${error.response.data.message}`);
      } else {
        setErrorMessage("Bir hata oluştu. Profil güncellenemedi.");
      }
    }
  };

  // Form inputları değiştiğinde userData'yı güncelle
  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Çıkış yapma işlemi
  const handleLogout = () => {
    localStorage.removeItem("username"); // Kullanıcı adını localStorage'dan temizle
    navigate("/"); // Anasayfaya yönlendir
  };

  return (
    <div className="profile-container">
      <h1>Profilim</h1>
      {message && <p className="success-message">{message}</p>}
      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <form onSubmit={handleProfileUpdate} encType="multipart/form-data">
        <div className="profile-info">
          <div className="profile-fields">
            <label>
              Ad:
              <input
                type="text"
                name="firstName"
                value={userData.firstName}
                onChange={handleChange}
                required
              />
            </label>
            <label>
              Soyad:
              <input
                type="text"
                name="lastName"
                value={userData.lastName}
                onChange={handleChange}
                required
              />
            </label>
            <label>
              E-posta:
              <input
                type="email"
                name="email"
                value={userData.email}
                onChange={handleChange}
                required
              />
            </label>
            <label>
              Doğum Tarihi:
              <input
                type="date"
                name="birthdate"
                value={userData.birthdate}
                onChange={handleChange}
              />
            </label>
            <label>
              Doğum Yeri:
              <input
                type="text"
                name="birthplace"
                value={userData.birthplace}
                onChange={handleChange}
              />
            </label>
            <label>
              Cinsiyet:
              <input
                type="text"
                name="gender"
                value={userData.gender}
                onChange={handleChange}
                placeholder="Erkek, Kadın, Diğer"
              />
            </label>
          </div>
        </div>

        <div className="profile-buttons">
          <button type="submit">Profilimi Güncelle</button>
          <button type="button" onClick={handleLogout}>
            Çıkış Yap
          </button>
        </div>
      </form>
    </div>
  );
};

export default Profile;

import React from "react";
import "./CategoryCard.css";

const CategoryCard = ({ title, image, description, onClick }) => {
  return (
    <div className="box" onClick={onClick}>
      <img src={image} alt={title} />
      <h3>{title}</h3>
      <p>{description}</p>
      <button className="btn">{title}</button>
    </div>
  );
};

export default CategoryCard;

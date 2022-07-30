import React from "react";
import "../featured-clubs.css";

function featuredClubContent() {
  return (
    <div className="featured-club-content-container">
      <h2>[Club Name]</h2>
      <div className="featured-club-inner-img-div">
        <p>Image Placeholder</p>
      </div>
      <div className="featured-club-inner-description-div">
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
          ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit
          in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
          sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
          mollit anim id est laborum.</p>
      </div>
    </div>
  );
}

export default featuredClubContent;
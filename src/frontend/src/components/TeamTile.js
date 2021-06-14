import { React } from "react";
import { Link } from "react-router-dom";
import "./TeamTile.scss";

export const TeamTile = ({ teamName }) => {
  const teamRoute = `/teams/${teamName}`;
  return (
    <div className="TeamTile">
      <h1>
        <Link to={teamRoute}>{teamName}</Link>
      </h1>
    </div>
  );
};

export default TeamTile;

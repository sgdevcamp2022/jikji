import "./ItemBoxTop.css";
import "../../FontAwesome.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ItemBoxTop = (props) => {
  // 현재 선택된 아이콘을 관리하는 state

  return (
    <div className="itemboxtop">
      <p className="artist">{props.userId}</p>
    </div>
  );
};

export default ItemBoxTop;

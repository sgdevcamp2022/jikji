import "./ItemBoxBottom.css";
import "../../FontAwesome.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ItemBoxBottom = (props) => {
  // 현재 선택된 아이콘을 관리하는 state

  return (
    <div className="itemboxbottom">
      <div className="itemboxbottom-toComment">댓글 n개 모두 보기</div>
      <form>
        <input
          type="text"
          placeholder="댓글 달기..."
          className="itemboxbottom-input"
        ></input>
        <input type="submit" value="게시"></input>
      </form>
    </div>
  );
};

export default ItemBoxBottom;

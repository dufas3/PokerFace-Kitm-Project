import { useNavigate, useSearchParams } from "react-router-dom";
import { useCallback, useEffect, useState } from "react";

function RouteManager() {
  const [searchParams] = useSearchParams();
  const [navig, setNavig] = useState();
  const navigate = useNavigate();
  const handleNavigate = useCallback(() => navig, [navigate]);

  useEffect(() => {
    if (searchParams.get("roomId") == undefined) {
      setNavig(navigate("/Login", { replace: true }));
    } else {
      setNavig(
        navigate("/Join?roomId=" + searchParams.get("roomId"), {
          replace: true,
        })
      );
    }
  });

  return <></>;
}

export default RouteManager;

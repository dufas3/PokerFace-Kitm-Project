const ConnectionUrl = ({ appendix }) => {
  return new URL(
    "https://pokerface-devbackend.azurewebsites.net/api" + appendix
  );
};

export const WebsiteUrl = "https://pokerface-dev.azurewebsites.net";
export default ConnectionUrl;

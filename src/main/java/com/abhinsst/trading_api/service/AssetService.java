package com.abhinsst.trading_api.service;

import java.util.List;

import com.abhinsst.trading_api.model.Asset;
import com.abhinsst.trading_api.model.Coin;
import com.abhinsst.trading_api.model.User;

public interface AssetService {

  Asset createAsset(User user, Coin coin, double quantity);

  Asset getAssetById(Long assetId) throws Exception;

  Asset getAssetByUserIdAndId(Long userId, Long assetId);

  List<Asset> getUserAssets(Long userId);

  Asset updateAsset(Long assetId, double quantity) throws Exception;

  Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

  void deleteAsset(Long assetId);

}

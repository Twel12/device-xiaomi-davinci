#
# Copyright (C) 2019 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

$(call inherit-product, device/xiaomi/davinci/device.mk)

# Inherit some common aosp stuff.
TARGET_BOOT_ANIMATION_RES := 1080
TARGET_GAPPS_ARCH := arm64
TARGET_INCLUDE_STOCK_ARCORE := true
TARGET_SUPPORTS_GOOGLE_RECORDER := false
TARGET_INCLUDE_WIFI_EXT := true
$(call inherit-product, vendor/aosp/config/common_full_phone.mk)

# Device identifier. This must come after all inclusions.
PRODUCT_BRAND := Xiaomi
PRODUCT_DEVICE := davinci
PRODUCT_MANUFACTURER := Xiaomi
PRODUCT_NAME := aosp_davinci
PRODUCT_MODEL := Mi 9T


# FOD animations
EXTRA_FOD_ANIMATIONS := true

# Inherit from custom vendor
# $(call inherit-product, vendor/ANXCamera/config.mk)

# Inherit PixelGApps
# $(call inherit-product, vendor/google/gms/config.mk)

# We're shipping build with Gapps
# WITH_GMS := true

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

# Face Unlock
TARGET_FACE_UNLOCK := true
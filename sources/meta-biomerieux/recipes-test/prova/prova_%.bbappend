FILESEXTRAPATHS:prepend := "${THISDIR}/custom:"

SRC_URI:append = " file://prova.conf"

do_install:append() {
    install -d ${D}${sysconfdir}
    install -m 0644 prova.conf ${D}${sysconfdir}
}

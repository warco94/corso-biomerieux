SECTION = "devel"
SUMMARY = "Linux Trace Toolkit KERNEL MODULE"
DESCRIPTION = "The lttng-modules 2.0 package contains the kernel tracer modules"
HOMEPAGE = "https://lttng.org/"
LICENSE = "LGPLv2.1 & GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0464cff101a009c403cd2ed65d01d4c4"

inherit module

include lttng-platforms.inc

SRC_URI = "https://lttng.org/files/${BPN}/${BPN}-${PV}.tar.bz2 \
           file://0001-Fix-compaction-migratepages-event-name.patch \
           file://0002-Fix-tracepoint-event-allow-same-provider-and-event-n.patch \
           file://0003-fix-sched-tracing-Don-t-re-read-p-state-when-emittin.patch \
           file://0004-fix-block-remove-genhd.h-v5.18.patch \
           file://0005-fix-scsi-block-Remove-REQ_OP_WRITE_SAME-support-v5.1.patch \
           file://0006-fix-random-remove-unused-tracepoints-v5.18.patch \
           file://0007-fix-kprobes-Use-rethook-for-kretprobe-if-possible-v5.patch \
           file://0008-fix-scsi-core-Remove-scsi-scsi_request.h-v5.18.patch \
           file://0009-Rename-genhd-wrapper-to-blkdev.patch \
           file://0010-fix-mm-compaction-cleanup-the-compaction-trace-event.patch \
          "

# Use :append here so that the patch is applied also when using devupstream
SRC_URI:append = " file://0001-src-Kbuild-change-missing-CONFIG_TRACEPOINTS-to-warn.patch"

SRC_URI[sha256sum] = "7cf1acbb50b84116acc9b4281b81dcc2643d6018bbd1e8514ad1270239896c4b"

export INSTALL_MOD_DIR="kernel/lttng-modules"

EXTRA_OEMAKE += "KERNELDIR='${STAGING_KERNEL_DIR}'"

MODULES_MODULE_SYMVERS_LOCATION = "src"

do_install:append() {
	# Delete empty directories to avoid QA failures if no modules were built
	if [ -d ${D}/${nonarch_base_libdir} ]; then
		find ${D}/${nonarch_base_libdir} -depth -type d -empty -exec rmdir {} \;
	fi
}

python do_package:prepend() {
    if not os.path.exists(os.path.join(d.getVar('D'), d.getVar('nonarch_base_libdir')[1:], 'modules')):
        bb.warn("%s: no modules were created; this may be due to CONFIG_TRACEPOINTS not being enabled in your kernel." % d.getVar('PN'))
}

BBCLASSEXTEND = "devupstream:target"
LIC_FILES_CHKSUM:class-devupstream = "file://LICENSE;md5=0464cff101a009c403cd2ed65d01d4c4"
DEFAULT_PREFERENCE:class-devupstream = "-1"
SRC_URI:class-devupstream = "git://git.lttng.org/lttng-modules;branch=stable-2.13"

SRCREV:class-devupstream = "c570be0da77e963d77bac099d468bc0cd5f1bd63"
PV:class-devupstream = "2.13.0+git${SRCPV}"
S:class-devupstream = "${WORKDIR}/git"
SRCREV_FORMAT ?= "lttng_git"

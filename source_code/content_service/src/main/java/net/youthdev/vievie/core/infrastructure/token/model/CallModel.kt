package net.youthdev.vievie.infrastructure.token.model

class CallModel(var callType: String?,
                var callerAvatar: String?,
                var callerFirstName: String?,
                var callerLastName: String?,
                var callerAge: Int,
                var callerGender: String?,
                var callerAddress: String?,
                var caseQuestion: String?,
                var caseQuestionImages: List<String>?,
                var receiverIdentity: String?,
                var receiverFirstName: String?,
                var receiverAccountId: Int,
                var patientId: Int,
                var doctorId: Int,
                var vieVieCaseId: Int)
